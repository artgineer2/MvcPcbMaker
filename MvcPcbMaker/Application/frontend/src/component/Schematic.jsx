 import React, { Component} from 'react';
import { Container, Row, Col,Form, DropdownButton, Dropdown} from 'react-bootstrap';
import ProjectDataService from '../service/ProjectDataService';

class Schematic extends Component
{
	constructor(props) {
		super(props);
		this.state = {
			schemId:'Schematic1',
			selectedFile: '',
			fileUploaded: false,
			boardColumnCount:4
		};

		this.schematicUploaded = this.schematicUploaded.bind(this)
		this.schematicSelected = this.schematicSelected.bind(this)
		this.onColumnChange = this.onColumnChange.bind(this)
	}


	schematicSelected(e)
	{
		console.log(e.target.id);
		//this.props.schemSel(e.target.id);
		this.setState({schemId: e.target.id});
		ProjectDataService.sendSchematicId(e.target.id)
			.then(response => {
				console.log(response);
				this.props.schemSel(this.state.schemId);
		})
	}

	schematicUploaded()
	{
		this.props.schematicDataUploaded(this.state.fileUploaded);
	}

	onColumnChange(e)
	{
		this.props.onBoardColumnChange(e.target.value);
	}


	render()
	{
		let status = "";
		if(this.props.processStatus === "ready")
		{
			status = "Rendering board";
		}
		else
		{
			status = this.props.processStatus;
		}
    return(

	<Container>
	<Row>
	<Col md={4}>
		<Row>
			<Col>
				<DropdownButton id="dropdown-basic-button" title="Choose A Schematic">
				  <Dropdown.Item onClick={this.schematicSelected} id="Schematic1" >
					Schematic1
					</Dropdown.Item>
				  <Dropdown.Item onClick={this.schematicSelected} id="Schematic2" >
					Schematic2
					</Dropdown.Item>
				  <Dropdown.Item onClick={this.schematicSelected} id="Schematic3" >
					Schematic3
					</Dropdown.Item>
				  <Dropdown.Item onClick={this.schematicSelected} id="Schematic4" >
					Schematic4
					</Dropdown.Item>
				  <Dropdown.Item onClick={this.schematicSelected} id="Schematic5" >
					Schematic5
					</Dropdown.Item>
				  <Dropdown.Item onClick={this.schematicSelected} id="Schematic6" >
					Schematic6
					</Dropdown.Item>
				  <Dropdown.Item onClick={this.schematicSelected} id="Schematic7" >
					Schematic7
					</Dropdown.Item>
				</DropdownButton>
			</Col>
		</Row>
		<Row>
			<Col>
				{this.state.schemId}:{status}
			</Col>
		</Row>
	</Col>
	<Col md={4}>
		<Form.Label>Number of Layout Columns</Form.Label>
			<Row>
				<Col>
		        <Form.Check type="radio" name="columnCountRadios"
		          label="2" id="2LayoutColumns" value="2"
					onChange={this.onColumnChange}/>
				</Col>
				<Col>
		        <Form.Check type="radio" name="columnCountRadios"
		          label="3" id="3LayoutColumns" value="3"
					onChange={this.onColumnChange}/>
				</Col>
				<Col>
		        <Form.Check type="radio" name="columnCountRadios"
		          label="4" id="4LayoutColumns" value="4"
 					onChange={this.onColumnChange}/>
				</Col>
				<Col>
		        <Form.Check type="radio" name="columnCountRadios"
		          label="5" id="5LayoutColumns" value="5"
					onChange={this.onColumnChange}/>
				</Col>
				<Col>
		        <Form.Check type="radio" name="columnCountRadios"
		          label="6" id="6LayoutColumns" value="6"
		        	onChange={this.onColumnChange}/>
				</Col>
			</Row>

	</Col>
	<Col md={4}>
 	</Col>
	</Row>
	</Container>

   	)
     }
}


export default Schematic
