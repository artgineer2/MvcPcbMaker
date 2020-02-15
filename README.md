# MvcPcbMaker


As an electrical engineer, I've designed and laid-out several printed circuit boards (PCBs), mostly for side projects.  

The biggest hassle for creating a PCB is doing the lay out manually.  Most EDA software has an auto-router that does the trace routing for you, but you still have to place the components on the PCB.  To get the best results from auto-router the components need to be placed according to the connection counts between them, with the components with the highest connection count being closest to each other (allowing enough room for trace layout.  In some cases the auto-router will not be able to route all of the traces, and component placement must be adjusted, making the layout process iterative and sometimes tedious.  This becomes significantly more difficult when you have to get the PCB within given size constraints.

My focus in college with integrated circuit design, and I had an internship where I learn about the existance of auto-place-and-route software for IC design.  It eventually occured to me that the same concept could be applied to PCB layout.

I originally started the project in Python, but decided to re-do the project in Java for performance reasons (and to get more Java experience).
