package com.mvcpcbmaker.controller;

import java.io.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mvcpcbmaker.services.BoardService;

import com.mvcpcbmaker.services.SchematicService;



@RestController
public class PcbPlaceRouteController {
	private String processStatus;
	private String schematicData;
	private String boardLayoutJsonString;
	private int schematicProcessState;


	private String fileName;
	private String name;

	@Autowired
	SchematicService schematicService;
	@Autowired
	BoardService boardService;


	@GetMapping(value="/sendSchematicId/{id}")
	public ResponseEntity<String> sendSchematicId(@PathVariable String id)
	{

		try
		{
			System.out.println(id);
			this.fileName = id+".sch";
			this.name = id;

			this.schematicProcessState = 0;
			processStatus = "initializing";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return new ResponseEntity<String>(processStatus,HttpStatus.OK);
	}

	@GetMapping(value="/getProcessStatus")
	public ResponseEntity<String> getProcessingStatus()
	{
		try
		{
				switch(this.schematicProcessState)
				{
				case 0:
					this.schematicService.cleanOutObjectData();
					this.boardService.cleanOutObjectData();
					this.schematicService.cleanOutDatabase();
					try
					{
						System.gc();
						Thread.sleep(3000);
					}
					catch(Exception e)
					{
						System.out.print("Garbage Collection error: ");
						e.printStackTrace();
					}
					this.schematicProcessState++;
					break;
				case 1:
					processStatus = "loading schematic data";
					try(InputStream schFileStream = getClass().getResourceAsStream("/schematics/"+this.fileName);
						BufferedReader schBufReader = new BufferedReader(new InputStreamReader(schFileStream));)
					{
						this.schematicData = schBufReader.lines().collect(Collectors.joining(System.lineSeparator()));
					}
					this.schematicProcessState++;
					break;
				case 2:
					processStatus = "creating schematic map ";
					this.schematicService.createSchematicMaps(
							this.name, this.schematicData);


					this.schematicProcessState++;
					break;
				case 3:
					processStatus = "creating primary DB tables ";
					this.schematicService.createPrimaryDBTables();

					this.schematicProcessState++;
					break;
				case 4:
					processStatus = "creating section DB tables";
					this.schematicService.createSectionTables();


					this.schematicProcessState++;
					break;
				case 5:
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
