# MvcPcbMaker (NOTE: This is no longer running on AWS)

## Background

As an electrical engineer, I've designed and laid-out several printed circuit boards, mostly for side projects.

The biggest hassle for creating a PCB is doing the lay out manually.  Most EDA software has an auto-router that does the trace routing for you, but you still have to place the components on the PCB.  To get the best results from auto-router the components need to be placed according to the connection counts between them, with the components with the highest connection count being closest to each other (allowing enough room for trace layout.  In some cases the auto-router will not be able to route all of the traces, and component placement must be adjusted, making the layout process iterative and sometimes tedious.  This becomes significantly more difficult when you have to get the PCB within given size constraints.

My focus in college with integrated circuit design, and I had an internship where I learn about the existance of auto-place-and-route software for IC design.  It eventually occured to me that the same concept could be applied to PCB layout.

## The Project

The application takes in an EAGLE schematic file (XML format) and parses it in to four maps: PCB footprints (component package), components (vendor and component information), parts (from the schematic), and nets (part interconnections).  These maps are then converted to lists for creating the database tables, using MySQL.  This project is a hybrid MVC/ETL application, where data is extracted from the XML schematic file using an XML parser, then transformed by loading the lists into primary database tables from which intermediary tables (parent_parts and child_parts) are derived and used in a stored procedure (find_closest_parent) to enter data into a final table (closest_parent).  The closest_parent table data is used to create the Section tables, who's names contain the parent part of each section table.  The section tables represent physical sections on the PCB layout, which contain the parent part and the child parts of said parent part.  

At this point, project is still a work in progress (I haven't even started on the auto-routing part), and current algorithms for placing the parts on the PCB still need some work, though they seem do a decent job of collecting child parts into their parent part sections.



