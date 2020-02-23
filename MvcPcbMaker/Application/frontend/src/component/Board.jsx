import React from 'react';
//import Project from './Project';
//import ProjectDataService from '../service/ProjectDataService';
import {Container, Row, Col} from 'react-bootstrap';

class Part extends React.Component {
	render() {

    return (<rect id={this.props.name} width={this.props.width} height={this.props.height}
			x={this.props.centerX-(this.props.width/2)} y={this.props.centerY-(this.props.height/2)}
			stroke="black" stroke-width="1" fill={this.props.color}/>)
  }
}

class Section extends React.Component {
	constructor(props)
	{
		super(props);
		this.state = this.props;
	}

	render()
	{

		var sectionSvg = [];
		var partsSvg = [];
		/*var sectionCenterX = this.props.centerX;
		var sectionCenterY =  this.props.centerY;
		var sectionWidth = this.props.width;
		var sectionHeight = this.props.height;*/

		let columnLeftEdge = this.props.centerX - this.props.width/2.0;
		let columnRightEdge = this.props.centerX + this.props.width/2.0;
		let sectionTopEdge = this.props.centerY - this.props.height/2.0;
		let sectionBottomEdge = this.props.centerY + this.props.height/2.0;

		var sectionGridPoints = columnLeftEdge+","+sectionBottomEdge+" "+
					columnLeftEdge+","+sectionTopEdge+" "+
					columnRightEdge+","+sectionTopEdge+" "+
					columnRightEdge+","+sectionBottomEdge;

		//sectionSvg.push(<polygon points={sectionGridPoints} stroke="green" stroke-width="1"  fill="white"/>);

		var childParts = this.props.parts.childPartsGrid;
		var parentPartSides = this.props.parts.parentPartSides;
		var childGridColumnCellCount = this.props.parts.childPartGridColumnCellCount;
		var childGridRowCellCount = this.props.parts.childPartGridRowCellCount;
		var parentPartProps = {
			name: this.state.parts.parentPartName,
			width:this.state.parts.parentPartWidth,
			height:this.state.parts.parentPartHeight,
			centerX:this.state.parts.centerX,
			centerY:this.state.parts.centerY,
			color: "black"
		};

		//sectionSvg.push(<g>);

		partsSvg.push(<Part {...parentPartProps}/>);

		for(let side = 0; side < parentPartSides; side++)
		{
			let sideData = childParts[side];
			if(sideData != null)
			{
				for (let rowIndex = 0; rowIndex < childGridRowCellCount; rowIndex++ )
				{
					let rowData = sideData[rowIndex];
					if(rowData != null)
					{
						for (let columnIndex = 0; columnIndex < childGridColumnCellCount; columnIndex++ )
						{
							let columnData = rowData[columnIndex];
							if(columnData != null)
							{
								let childPartProps = {
									name:  childParts[side][rowIndex][columnIndex].name,
									height: childParts[side][rowIndex][columnIndex].height,
									width: childParts[side][rowIndex][columnIndex].width,
									centerX: childParts[side][rowIndex][columnIndex].centerX,
									centerY: childParts[side][rowIndex][columnIndex].centerY,
									color: "gray"
								};

							 	partsSvg.push(<Part {...childPartProps} />);
							}
						}
					}
				}
			}
		}

		sectionSvg.push(<g>{partsSvg}</g>);


		return sectionSvg;



	}
}

class Board extends React.Component {
	constructor(props)
	{
		super(props);

		this.state = {boardJsonData: this.props.boardJsonData,
					width:this.props.boardJsonData.boardWidth,
					height:this.props.boardJsonData.boardHeight,
					 processStatus:this.props.processStatus,
					};

	}

	render()
	{
		var boardJsonData = this.props.boardJsonData;
		console.log("Board render: " + this.props.processStatus);
		console.log("board boardJsonData: ", this.props.boardJsonData);

		var boardPoints = "0,0 " + boardJsonData.boardWidth + ",0 " +
				boardJsonData.boardWidth + "," + boardJsonData.boardHeight + " 0," + boardJsonData.boardHeight
		var boardSvg;
		var section2BoardOffsetX = (boardJsonData.boardWidth - boardJsonData.sectionsTotalWidth)/2.0;
		var section2BoardOffsetY = (boardJsonData.boardHeight - boardJsonData.sectionsTotalHeight)/2.0;

		var sectionsSvg = [];
		var gridSvg = [];
		var column = {
					EdgeXCoord:0.0,
					SectEdgeYCoord:[],
					centerPoints:[]
				};
		var grid = []
		if(this.props.processStatus !== "ready")
		{
			boardSvg = <g></g>;
			gridSvg = <g></g>;
			sectionsSvg = <g></g>;
		}
		else
		{
			if(this.props.boardJsonData.name != null)
			{


				if(this.props.boardJsonData.name === "clear")
				{
					boardSvg = "";

				}
				else
				{

					boardSvg =  <polygon points={boardPoints} stroke="green" strokeWidth="1"  fill="darkgreen"/>;
					let XPos = section2BoardOffsetX;

					for(let colIndex = 0; colIndex <= boardJsonData.columnWidths.length; colIndex++ )
					{
						column = {
							EdgeXCoord:0.0,
							SectEdgeYCoord:[],
							centerPoints:[]
						};

						column.EdgeXCoord = XPos;
						grid[colIndex] = column;
						if(colIndex < boardJsonData.columnWidths.length)
						{
							XPos += boardJsonData.columnWidths[colIndex];
						}
					}
					if(grid.length > 1)
					{
						grid.forEach((item,index)=>
						{
							let YPos = section2BoardOffsetY;
							column = boardJsonData.sectionColumns[index];
							if(column != null)
							{
								for(let sectIndex = 0; sectIndex <= column.length; sectIndex++)
								{
									item.SectEdgeYCoord[sectIndex] = YPos;
									if(sectIndex < column.length)
									{
										YPos += column[sectIndex].height;
									}
									if(column[sectIndex] != null)
									{
										item.centerPoints[sectIndex] = {};
										item.centerPoints[sectIndex]["centerX"]	= column[sectIndex].centerX;
										item.centerPoints[sectIndex]["centerY"]	= column[sectIndex].centerY;
									}
								}
							}
						})

						for(let i = 0; i < grid.length-1; i++)
						{
							let columnLeftEdge = grid[i].EdgeXCoord;
							let columnRightEdge = grid[i+1].EdgeXCoord;
							let EdgeYCoords = grid[i].SectEdgeYCoord;
							for(let j = 0; j < EdgeYCoords.length-1; j++)
							{
								let sectionBottomEdge = EdgeYCoords[j];
								let sectionTopEdge = EdgeYCoords[j+1];

								var sectionGridPoints = columnLeftEdge+","+sectionBottomEdge+" "+
											columnLeftEdge+","+sectionTopEdge+" "+
											columnRightEdge+","+sectionTopEdge+" "+
											columnRightEdge+","+sectionBottomEdge;

								var sectEdges = <polygon points={sectionGridPoints} stroke="green" stroke-width="1"  fill="white"/>
								//gridSvg.push(sectEdges);
							}
						}
					}
					let sections = Object.keys(boardJsonData.sectionData);


					 for (var section of sections)
					 {
					 	var sectionProps = {
					 		name: section,
					 		centerX: boardJsonData.sectionData[section].centerX,
					 		centerY: boardJsonData.sectionData[section].centerY,
							width: boardJsonData.sectionData[section].width,
							height: boardJsonData.sectionData[section].height,
					 		parts: boardJsonData.sectionData[section]
						};
					 	sectionsSvg.push(<Section {...sectionProps} />)
					 }
				}
			}
		}

		return (
			<Container>
			<Row id="boardRowDiv" style={{border: "1px solid",height:"690px"}}>
				<Col xs lg="12" id="boardColDiv"  style={{width: "100%", height: "100%", overflow: "scroll", textAlign: "center"}}>
				<svg  id="board" width={boardJsonData.boardWidth} height={boardJsonData.boardHeight}
					style={{margin: "0", position: "relative", top: "40%", left: "0%"}} transform={"scale(2.0)"}>
					<g>
						{boardSvg}
						{gridSvg}
						{sectionsSvg}

					</g>
				</svg>


				</Col>
			</Row>
			</Container>

			)

	}
}


export default Board


/*
<div id="boardRowDiv"  className="row"  style={{border: "1px solid",height:"690px"}}>
	<div id="boardColDiv" className="col-md-12" style={{width: "100%", height: "100%", overflow: "scroll", textAlign: "center"}}>
	<svg  id="board" width={boardJsonData.boardWidth} height={boardJsonData.boardHeight}
		style={{margin: "0", position: "relative", top: "40%", left: "0%"}} transform={"scale(2.0)"}>
		<g>
			{boardSvg}
			{gridSvg}
			{sectionsSvg}

		</g>
	</svg>


	</div>
</div>


*/
