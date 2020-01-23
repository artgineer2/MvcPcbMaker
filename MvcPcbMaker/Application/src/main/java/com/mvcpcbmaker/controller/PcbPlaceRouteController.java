package com.mvcpcbmaker.controller;

import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mvcpcbmaker.AppConfiguration;
import com.mvcpcbmaker.daos.PackageDao;
import com.mvcpcbmaker.services.BoardService;
//import com.mvcpcbmaker.models.schematic.SchematicData;

import com.mvcpcbmaker.services.SchematicService;

import org.springframework.core.task.TaskExecutor;


//import com.mvcpcbmaker.services.PlaceRouteServices;

//@CrossOrigin(origins = { "http://3.136.140.150:3000", "http://3.136.140.150:4200" })
//@CrossOrigin(origins = { "http://127.0.0.1:3000", "http://127.0.0.1:4200", "http://127.0.0.1:8080" })
@RestController
public class PcbPlaceRouteController {
	private int statusCount;
	private String processStatus;
	private String schematicData;
	private String boardLayoutJsonString;
	private int schematicProcessState;
	private boolean subProcessDone;


	private String fileName;
	private String name;

	@Autowired
	SchematicService schematicService;
	@Autowired
	BoardService boardService;




	/*@PostMapping(value="/uploadSchematicData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//, method=RequestMethod.POST, consumes=MediaType.ALL_VALUE)
	public ResponseEntity<Void> uploadSchematicData(@RequestParam MultipartFile file)
	{
		this.processStatus = "";
		try
		{

			this.fileName = file.getOriginalFilename();


			this.name = this.fileName;

			this.schematicData = new String(file.getBytes());
			this.schematicService.cleanOutDatabase();
			this.schematicProcessState = 0;

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ResponseEntity.ok().build();//return "File uploaded";// redirect to processing status update page ("pinger")
	}*/

	@GetMapping(value="/sendSchematicId/{id}")
	public ResponseEntity<Void> sendSchematicId(@PathVariable String id)
	{

		//this.schematicData = new String();
		try
		{
			System.out.println(id);
			this.fileName = id+".sch";
			this.name = id;

			processStatus = "loading schematic data";
			try(InputStream schFileStream = getClass().getResourceAsStream("/schematics/"+this.fileName);
				BufferedReader schBufReader = new BufferedReader(new InputStreamReader(schFileStream));)
			{
				this.schematicData = schBufReader.lines().collect(Collectors.joining(System.lineSeparator()));
			}

            		System.out.println(this.schematicData.substring(0, 100));
			

			this.boardService.cleanOutObjectData();
            		this.schematicService.cleanOutDatabase();
			/*try
			{
			    System.gc();
			    Thread.sleep(3000);
			}
			catch(Exception e)
			{
			    System.out.print("Garbage Collection error: ");
			    e.printStackTrace();
			}*/

			this.schematicProcessState = 0;

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping(value="/getProcessStatus")//, method=RequestMethod.GET)
	public ResponseEntity<String> getProcessingStatus()
	{
		try
		{
				switch(this.schematicProcessState)
				{
				case 0:
					processStatus = "creating schematic map ";
					this.schematicService.createSchematicMaps(
							this.name, this.schematicData);


					this.schematicProcessState++;
					break;
				case 1:
					processStatus = "creating primary DB tables ";
					this.schematicService.createPrimaryDBTables();
					
					this.schematicProcessState++;
					break;
				case 2:
					processStatus = "creating section DB tables";
					this.schematicService.createSectionTables();
					this.schematicService.cleanOutObjectData();
					/*try
					{
					    System.gc();
					    Thread.sleep(3000);
					}
					catch(Exception e)
					{
					    System.out.print("Garbage Collection error: ");
					    e.printStackTrace();
					}*/


					this.schematicProcessState++;
					break;
				case 3:
					processStatus = "ready";
					break;
				default:;
				}
			System.out.println(processStatus);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<String>(processStatus,HttpStatus.OK);
	}


	@GetMapping(value="/getBoardData/{columns}")
	public ResponseEntity<String> getBoardData(@PathVariable int columns)
	{

		this.boardLayoutJsonString = this.boardService.createBoardLayout(this.name, columns).toString();

		return new ResponseEntity<String>(this.boardLayoutJsonString,HttpStatus.OK);
	}



}
