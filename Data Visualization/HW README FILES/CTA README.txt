
# Project README

## Project Title: Tracking CTA Ridership

### Project Overview
This project involves the visualization and analysis of daily ridership data from the Chicago Transit Authority (CTA) rail-car train system. The aim is to uncover trends influenced by various factors such as weather conditions and significant events. By integrating multiple datasets, we have developed a comprehensive analysis and interactive visualizations to provide deeper insights into CTA ridership patterns.

### Technologies Used
- **Programming Languages**: R, SQL
- **Libraries**: ggplot2, dplyr, shiny, leaflet, sf, rgdal, lubridate, raster, stringr
- **Tools**: RStudio, Shiny, Tableau
- **Data Sources**: CTA's open-source database, Meteoblue Weather Data Archive

### File Descriptions

#### 1. **app.R**
This file contains the R script for the Shiny application used to visualize CTA ridership data. The app includes interactive features allowing users to explore the impact of weather and events on ridership. It includes:
- User interface (UI) setup with navigation bars and input elements.
- Server logic to render plots and maps based on user input.
- Reactive expressions to filter and process data for visualization.

#### 2. **ctalines.dbf**
A database file containing attribute data for CTA rail lines. This file is part of the shapefile set representing the geographical layout of the rail lines. It is used to map ridership data spatially.

#### 3. **ctalines.prj**
A projection file defining the coordinate system and map projection for the CTA rail lines shapefile. It ensures that the spatial data aligns correctly on maps.

#### 4. **ctalines.shp**
The main shapefile containing geometric data for CTA rail lines. It includes the shapes and locations of the rail lines, used in spatial visualizations to map ridership data.

#### 5. **ctalines.shx**
An index file that accompanies the shapefile, allowing for efficient spatial querying and rendering of the CTA rail lines.

#### 6. **dailyDataClean.csv**
A cleaned and processed dataset containing daily ridership totals, merged with weather data. It includes variables such as date, station ID, ridership totals, day type (weekday, Saturday, Sunday/holiday), and weather attributes (temperature, precipitation, snowfall).

#### 7. **lineDataShp.csv**
A CSV file containing shapefile data for each CTA line, linking spatial attributes with ridership data. It includes station IDs, line types, and geographic coordinates.

#### 8. **totalDailyJoin.csv**
A merged dataset combining daily ridership totals with weather data and geographic information. It is used in the Shiny app to visualize the relationship between ridership and weather conditions.

#### 9. **CTA_Train_Traffic.dcf**
A data configuration file (DCF) for the CTA train traffic project. It includes metadata and configuration settings used in the project for data processing and visualization.

#### 10. **eventHandling.R**
An R script for handling and processing event data related to CTA ridership. It includes functions to filter and analyze ridership data during significant events like Lollapalooza and Chicago Cubs games.

#### 11. **Final Report.pdf**
A comprehensive report detailing the project's objectives, methodology, data analysis, and findings. It includes:
- Dataset descriptions and attribute details.
- Data cleaning and exploratory analysis procedures.
- Explanatory visualizations and their interpretations.
- Discussion of results and key takeaways.
- Appendices with additional visualizations and code snippets.

#### 12. **weatherDataCleaning (1).R**
An R script for cleaning and processing weather data. It includes functions to format dates, handle missing values, and merge weather data with ridership data for analysis.

### Problem Solved
The project addresses the challenge of understanding factors influencing CTA ridership, focusing on:
- **Weather Impact**: Analyzing how different weather conditions (temperature, precipitation, snowfall) affect ridership.
- **Event Impact**: Investigating the effects of significant events on ridership patterns.
- **Ridership Trends**: Identifying consistent patterns in daily ridership and understanding anomalies.

### Key Findings
- **Weather Impact**: Minimal overall impact on daily ridership, with a notable exception on Sundays/Holidays where precipitation led to decreased ridership.
- **Event Impact**: Significant events like Lollapalooza and Chicago Cubs games cause spikes in ridership at nearby stations.
- **Consistency**: Weekday ridership remained relatively stable regardless of weather conditions, indicating commuters' reliance on public transportation for work.

### Learning Outcomes
- **Data Integration**: Gained proficiency in merging and cleaning large, disparate datasets.
- **Advanced Visualization**: Developed complex visualizations using R and Tableau, enhancing storytelling with data.
- **Interactive Dashboards**: Created Shiny apps that allow dynamic exploration of data, providing deeper insights through user interaction.
- **Collaboration**: Improved teamwork and communication skills through collaborative coding and project management.

### Usage Instructions
1. **Shiny Application**:
   - Run the `app.R` script in RStudio to launch the Shiny app.
   - Use the interactive features to explore ridership data based on weather conditions and events.

2. **Data Files**:
   - The CSV files (`dailyDataClean.csv`, `lineDataShp.csv`, `totalDailyJoin.csv`) contain cleaned and processed data for analysis and visualization.
   - The shapefiles (`ctalines.dbf`, `ctalines.prj`, `ctalines.shp`, `ctalines.shx`) provide spatial data for mapping ridership patterns.

3. **R Scripts**:
   - Use the provided R scripts (`eventHandling.R`, `weatherDataCleaning (1).R`) to process and analyze data related to specific events and weather conditions.

4. **Report**:
   - Refer to `Final Report.pdf` for a detailed explanation of the project's objectives, methodology, analysis, and findings.

### Acknowledgments
This project was completed as part of the Data Science coursework at DePaul University. Special thanks to the CTA for providing open-source data and Meteoblue for weather data. The collaborative efforts of all team members were instrumental in the success of this project.
