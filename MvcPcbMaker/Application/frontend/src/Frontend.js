
import React,  {Component} from 'react';
import Project from './component/Project';


class Frontend extends Component {
  render() {
    return (
      <div id="containerDiv" className="container">
        <Project />
      </div>
    );
  }
}

export default Frontend;
