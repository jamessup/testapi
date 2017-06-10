package com.app.james;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

 
@Controller
public class NoteController {
	
	/** The note. */
	Map<Integer, NoteList> noteMap;
	/** The map note id. */
	int mapNoteId;

	/**
	 * Instantiates a new note list controller.
	 */
	public NoteController() {
		noteMap = new HashMap<Integer, NoteList>();
		mapNoteId = 0;
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public @ResponseBody
	String getAllData() {

		Type baseType = new TypeToken<List<NoteList>>() {
		}.getType();
		Gson gson = new Gson();
		String str = gson.toJson(new ArrayList<NoteList>(noteMap.values()),
				baseType);
		return str;
	}

	/**
	 * Gets the note by id.
	 *
	 * @param id 
	 * @return id
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getById/{id}")
	public @ResponseBody
	String getById(@PathVariable Integer id) {
		boolean isFound = false;
		for (Entry<Integer, NoteList> e : noteMap.entrySet()) {
			NoteList value = e.getValue();
			if (value.getId().equals(id)) {
				isFound = true;
				if (isFound) {
					Gson gson = new Gson();
					return gson.toJson(value);
				}
			}
		}
		JsonObject jsonObject = new JsonObject();
		return jsonObject.toString();
	}

	/**
	 * Creates  
	 *
	 * @param note  
	 * @return the string of success or error id. If success return id and status
	 * ex success
	 * [{"success":true},{"id":0}]
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/input")
	public @ResponseBody
	String createnoteList(@ModelAttribute NoteList note,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//check subject 		
			if (note.getSubject().trim().isEmpty()) {
				JsonObject jsonObject = new JsonObject();
				JsonArray jsonArray = new JsonArray();
				jsonObject.addProperty("success", false);
				jsonArray.add(jsonObject);
				jsonArray = new JsonArray();
				jsonObject.addProperty("error", "Invalid input subject.");
				jsonArray.add(jsonObject);
				return jsonArray.toString();
			}
			//check status  	
			if (!note.getStatus().equals("ongoing")
					&& !note.getStatus().equals("done")) {
				JsonObject jsonObject = new JsonObject();
				JsonArray jsonArray = new JsonArray();
				jsonObject.addProperty("success", false);
				jsonArray.add(jsonObject);
				jsonArray = new JsonArray();
				jsonObject.addProperty("error", "Invalid input status.");
				jsonArray.add(jsonObject);
				return jsonArray.toString();
			}
			JsonObject jsonObject = new JsonObject();
			note.setId(mapNoteId);
			noteMap.put(mapNoteId++, note);
			JsonArray jsonArray = new JsonArray();

			jsonObject.addProperty("success", true);
			jsonArray.add(jsonObject);
			jsonObject = new JsonObject();
			jsonObject.addProperty("id", mapNoteId - 1);
			jsonArray.add(jsonObject);
			return jsonArray.toString();
		} catch (Exception ex) {
			JsonObject jsonObject = new JsonObject();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jsonObject.addProperty("success", false);
			return jsonObject.toString();
		}

	}

	/**
	 * Update note.
	 *
	 * @param note the noteList
	 * @param idInput the id input
	 * @param request the request
	 * @param response the response
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public @ResponseBody
	String updatenoteList(@ModelAttribute NoteList note,
			@RequestParam("id") Integer idInput, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			NoteList noteList = noteMap.get(idInput);
			noteList = note;
			noteMap.put(note.getId(),noteList);
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("success", true);
			return jsonObject.toString();
		} catch (Exception ex) {
			JsonObject jsonObject = new JsonObject();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jsonObject.addProperty("success", false);
			return jsonObject.toString();
		}
	}

	/**
	 * setstatue.
	 *
	 * @param statusInput the status input
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/setstatue")
	public @ResponseBody
	String updatenoteList(@RequestParam("statusInput") String statusInput,
			@RequestParam("id") Integer idInput, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			 
			if (!statusInput.equals("ongoing") && !statusInput.equals("done")) {
				JsonObject jsonObject = new JsonObject();
				JsonArray jsonArray = new JsonArray();
				jsonObject.addProperty("success", false);
				jsonArray.add(jsonObject);
				jsonArray = new JsonArray();
				jsonObject.addProperty("error", "Invalid input status.");
				jsonArray.add(jsonObject);
				return jsonArray.toString();
			}
			NoteList note = noteMap.get(idInput);
			note.setStatus(statusInput);
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("success", true);
			return jsonObject.toString();
		} catch (Exception ex) {
			JsonObject jsonObject = new JsonObject();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jsonObject.addProperty("success", false);
			return jsonObject.toString();
		}

	}

	/**
	 * Delete noteList.
	 *
	 * @param idInput the id input
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "delete/{idInput}")
	public @ResponseBody
	String deletenoteList(@PathVariable Integer idInput) {
		int id = -1;
		Boolean isFound = false;
		for (Entry<Integer, NoteList> e : noteMap.entrySet()) {

			id = e.getKey();
			NoteList value = e.getValue();
			if (value.getId().equals(idInput)) {
				isFound = true;

			}
		}
		if (isFound) {
			noteMap.remove(id);
			JsonObject jsonObject = new JsonObject();

			jsonObject.addProperty("success", true);
			return jsonObject.toString();
		} else {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("success", false);
			return jsonObject.toString();

		}
	}
}
