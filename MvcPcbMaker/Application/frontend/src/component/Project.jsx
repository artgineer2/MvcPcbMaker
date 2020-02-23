import React, { Component } from 'react';
import ProjectDataService from '../service/ProjectDataService';
import Schematic from './Schematic';
import Board from './Board';
import Description from './Description';
import {Container, Row, Col} from 'react-bootstrap';
//import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

class Project extends Component {
	constructor(props)
	{
		super(props);

		this.state = {
			columnCount:4,
			schematicId:1,
			schematicUploaded:false,
			processStatus:"",
			boardJson:{
				name:"",
				boardHeight:0,
				boardWidth:0,
				columnCount:0,
				columnWidths:[],
				sectionColumns:{},
				sectionData:{}
			}
		};
		this.dataUploaded = this.dataUploaded.bind(this);
		this.getProcessStatus = this.getProcessStatus.bind(this);
		this.stillProcessing = this.stillProcessing.bind(this);
		this.processingFinished = this.processingFinished.bind(this);
		this.getBoardLayout = this.getBoardLayout.bind(this);
		this.onColumnChange = this.onColumnChange.bind(this);
		this.schematicSelected = this.schematicSelected.bind(this);

		//this.startProcessStatusCheckTimer = this.startProcessStatusCheckTimer.bind(this);
	}

	dataUploaded(status)
	{
		console.log("schematicDataUploaded function ran: " + status);
		console.log(this.state.schematicUploaded);
		//this.setState({processStatus:"processing"},this.startProcessStatusCheckTimer);
		this.setState({boardJson:{}},this.getProcessStatus);

	}

	schematicSelected(id)
	{
		console.log("Project::schematicSelected: " + id);
		this.setState({boardJson:{}},this.getProcessStatus);
	}
	getProcessStatus()
	{
		ProjectDataService.getProcessStatus().then(
			response =>
			{response.data === 'ready' ? this.processingFinished() : this.stillProcessing(response.data) }

			)
	}

	stillProcessing(status)
	{
		console.log(status);
		this.setState({processStatus:status},this.getProcessStatus)
	}

	processingFinished()
	{

		console.log(this.state.processStatus);
		//clearInterval(this.processStatusCheckTimer)
		this.setState({processStatus:"ready"},this.getBoardLayout());

	}
	onColumnChange(value)
	{
		console.log("Project: " + value);
		this.setState({columnCount:value,boardJson:{}},this.getBoardLayout);

	}
	getBoardLayout()
	{
		console.log(this.state.processStatus);
		ProjectDataService.getBoardData(this.state.columnCount)
		.then(response => {
			this.setState({boardJson: response.data})
		})
	}

	render()
	{
		console.log("Project state: ", this.state.processStatus)
		const boardJsonData = this.state.boardJson;
		const processStatus = this.state.processStatus;

		console.log("Project render: " + boardJsonData);
		return (
				<Container>
					<Row>
						<Col xs lg="2">
							<Description />
						</Col>
						<Col xs lg="10">
							<Row>
								<Schematic
								schemSel={this.schematicSelected}
								schemId={this.state.schematicId}
								schematicUploaded={this.state.fileUploaded}
								schematicDataUploaded={this.dataUploaded}
								onBoardColumnChange={this.onColumnChange}
								processStatus={this.state.processStatus}/>
							</Row>
							<Row>
								<Board boardJsonData={boardJsonData} processStatus={processStatus} />
							</Row>
						</Col>
					</Row>
				</Container>
		)
	}
}



export default Project
