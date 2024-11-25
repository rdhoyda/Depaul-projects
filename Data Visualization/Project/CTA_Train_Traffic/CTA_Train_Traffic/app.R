#
# This is a Shiny web application. You can run the application by clicking
# the 'Run App' button above.
#
# Find out more about building applications with Shiny here:
#
#    http://shiny.rstudio.com/
#

#Load dependencies      
library(shiny)              #shiny app development
library(dplyr)              #tools for cleaning data
library(leaflet)            #map making
library(shinyWidgets)       #improved widget functionality            
library(rgdal)              #load in shapefile
library(ggplot2)            #data visualization
library(rsconnect)          #publish shiny app

#load data
df <- read.csv("dailyDataClean.csv")
weatherDf <- read.csv("totalDailyJoin.csv")

lineShp <- readOGR(dsn= ".",
                     layer = "ctalines", 
                     verbose = FALSE)

#convert to date var
df$date <- as.Date(df$date)

# Define UI for application
ui <- fluidPage(
    navbarPage("Transit Trackers",
               tabPanel("CTA Rider Distribution", icon = icon("city"),
                        mainPanel(
                            leafletOutput("mymap", height="89vh")),
                        
                            #Specify panel attributes
                            absolutePanel(top = 85, right = 40,width = "32vw", style = "background-color: white;
                            opacity: 0.85;
                            padding: 20px 20px 20px 20px;
                            margin: auto;
                            border-radius: 5pt;
                            box-shadow: 0pt 0pt 6pt 0px rgba(61,59,61,0.48);
                            padding-bottom: 2mm;
                            padding-top: 1mm;",
                                          
                                      #add title and intro text
                                      fluidRow(
                                          column(12,
                                                 align = "center",
                                                 tags$p(strong("CTA Ridership Impacts"), style = "font-size: 3vh;")),
                                          br(),
                                          column(width = 12,
                                                 align = "center",
                                                 tags$div(
                                                     "Use the dropdown to explore how CTA ridership changes as a result of time and line color. This map uses
                                                     data from ",
                                                     tags$a(href="https://www.transitchicago.com/data/", "transitchicago.com"),
                                                     "the CTA's open source data library. Click on map markers to expand
                                                     data points and learn more!",
                                                     br(), br()))),
                                      
                                      #Create dropdown selection box
                                      pickerInput("category", label = "Select Train Line", 
                                            choices = unique(df$genLineType),
                                            selected = unique(df$genLineType),
                                            multiple = TRUE,
                                            options = pickerOptions(
                                                    actionBox = TRUE,
                                                    #If all train lines are selected, show 'All Train Lines'
                                                    selectedTextFormat = paste0("count > ", length(sort(unique(df$genLineType))) -1),
                                                    countSelectedText = "All Train Lines")),
                                      
                                      #Create dropdown filter by daytype
                                      pickerInput("filterDay", label = "Filter by:", 
                                                  choices = unique(df$daytype),
                                                  selected = unique(df$daytype),
                                                  multiple = TRUE,
                                                  options = pickerOptions(
                                                      actionBox = TRUE,
                                                      #If all train lines are selected, show 'All Train Lines'
                                                      selectedTextFormat = paste0("count > ", length(sort(unique(df$daytype))) -1),
                                                      countSelectedText = "All Days Included")),
                                     
                                       #add date range input
                                      dateRangeInput(inputId = "date",
                                                     label = "Select a Date Range:",
                                                     start = "2022-01-01",
                                                     end = "2023-06-30",
                                                     min = "2022-01-01",
                                                     max = "2023-06-30",
                                                     format = "yyyy-mm-dd"),
                                      plotOutput("violinPlot"))),
               
               #Create a Weather Data panel
               tabPanel("Ridership & Precipitation", icon = icon("cloud-sun"),
                        mainPanel(
                            leafletOutput("mymap2", height= "89vh")
                        ),
                        
                        #Specify panel attributes
                        absolutePanel(top = 85, right = 40,width = "32vw", style = "background-color: white;
                            opacity: 0.85;
                            padding: 20px 20px 20px 20px;
                            margin: auto;
                            border-radius: 5pt;
                            box-shadow: 0pt 0pt 6pt 0px rgba(61,59,61,0.48);
                            padding-bottom: 2mm;
                            padding-top: 1mm;",
                                      
                                      
                                      #add title and intro text
                                      fluidRow(
                                          column(12,
                                                 align = "center",
                                                 tags$p(strong("Precipitation Impacts on Ridership during Sundays/Holidays"), style = "font-size: 3vh;")),
                                          br(),
                                          column(width = 10,
                                                 align = "center",
                                                 tags$div(
                                                     "Use the slider to explore how CTA ridership changed as a result of precipitation. 
                                                     This plot uses data from ",
                                                     tags$a(href="https://www.transitchicago.com/data/", "transitchicago.com"),
                                                     "the CTA's open source data library. Also, weather data from ",
                                                     tags$a(href = "https://www.meteoblue.com/en/weather/historyclimate/weatherarchive/chicago_united-states_4887398", "meteoblue.com"),
                                                     br(), br()))),
                                      #create slider input:
                                      sliderTextInput("rainSlider", "Amount of Rain",
                                                      choices = c(as.numeric(unique(weatherDf$sumPrecip))),
                                                      selected = range(vals = c(as.numeric(unique(weatherDf$sumPrecip))))),
                                      pickerInput("cat", label = "Select Train Line", 
                                                  choices = unique(weatherDf$genLineType),
                                                  selected = unique(weatherDf$genLineType),
                                                  multiple = TRUE,
                                                  options = pickerOptions(
                                                      actionBox = TRUE,
                                                      #If all train lines are selected, show 'All Train Lines'
                                                      selectedTextFormat = paste0("count > ", length(sort(unique(weatherDf$genLineType))) -1),
                                                      countSelectedText = "All Train Lines"))
                        ))
               
               #Create an About section
               #tabPanel("About")
               ))


# Define server logic required to draw visuals
server <- function(input, output,server) {
    
    #filter weather tab by slider input
    sliderChoice = reactive({
        dfSub = weatherDf|>
            filter(sumPrecip == input$rainSlider
                   & genLineType %in% input$cat)
    })
    
    # Reactive expression for the data subsetted to what the user selected
    filteredData <- reactive({
        dfSub <- df%>%
            filter(genLineType %in% input$category
                   & between(date,input$date[1], input$date[2])
                   & daytype %in% input$filterDay)%>%
            group_by(station_id, STATION_DESCRIPTIVE_NAME, Lat, Long, genLineType)%>%
            # summarise(totalRides = sum(rides), avgTemp = mean(Temperature..Mean.), 
            #           maxTemp = max(Temperature..Max.), minTemp = min(Temperature..Min.),
            #           avgRain = mean(Precipitation.Total..mm.), totalSnowfall = sum(Snowfall.Amount..cm.))
            summarise(totalRides = sum(rides), newAvgDailyRides = round(mean(rides),0), avgDailyRides = round(mean(avgDailyRides),0))
        
        
    })
    
    #filter for lines shapefile based on user input
    shapeData <- reactive({
        lineShp[lineShp@data$gnLnTyp %in% input$category,]
    })
    
    weatherShapeData <- reactive({
        lineShp[lineShp@data$gnLnTyp %in% input$cat,]
    })
    
    #Create map
    output$mymap <- renderLeaflet({
        
        #Create a palette & corresponding legend
        pal <- colorQuantile("Reds", filteredData()$totalRides, n = 5)
        palColors <- unique(pal(sort(filteredData()$totalRides))) 
        palBreaks <- round(quantile(filteredData()$totalRides, seq(0, 1, .2)),0)
        palLabels <- paste(lag(palBreaks), palBreaks, sep = " - ")[-1] # first lag is NA
        
        
        myMap <- leaflet()%>% #filters our data based on dropdown selection
        addProviderTiles(providers$CartoDB.Positron)%>% #adds a basemap
        addPolylines(data = shapeData(), color = "Gray")%>%
        addCircleMarkers(data = filteredData(),
                         ~Long, ~Lat,
                         popup = paste(
                             "<h5>Station: ", filteredData()$STATION_DESCRIPTIVE_NAME, "</h5>",
                             "<b>Selected Timeframe Daily Rider Avg: </b>", round(filteredData()$newAvgDailyRides,2), "<br>",
                             "<b>18 Month Daily Rider Avg: </b>", round(filteredData()$avgDailyRides,2), "<br>",
                             "<b>Selected Timeframe Total Riders: </b>", filteredData()$totalRides, "<br>"
                         ),
                         radius = 2,
                         label = ~STATION_DESCRIPTIVE_NAME,
                         color = ~pal(filteredData()$totalRides))%>%
        addLegend("bottomleft", colors = palColors, labels = palLabels, title = "Total Riders")
    })
    
    #define reactive value to store in modified plot
    updatedPlot <- reactiveVal(NULL)
    
    #define plot output
    output$violinPlot <- renderPlot({
        updatedPlot()
    })
    
    #update reactive value in observer
    observe({ 
        
        event <- input$mymap_marker_click
        #Check if marker was clicked
        if (!is.null(event$lng)) {
            updatedPlot(
                ggplot(data = filteredData(), aes(x = genLineType, y = totalRides/1000)) +
                    geom_violin() +
                    geom_point(data = filteredData()[filteredData()$Long == event$lng, ], aes(color = "Red", size = 5)) +
                    ylab("Number of Riders by the Thousand") +
                    xlab("")+ 
                    theme(axis.text.x = element_text(angle = 45, vjust = 0.5, hjust=1))+
                    theme(legend.position="none")
            )
        } else {
            updatedPlot(
                ggplot(data = filteredData(), aes(x = genLineType, y = totalRides/1000)) +
                    geom_violin() +
                    ylab("Number of Riders by the Thousand") +
                    xlab("")+
                    theme(axis.text.x = element_text(angle = 45, vjust = 0.5, hjust=1))+
                    theme(legend.position="none")
            )
        }
    })
    
    output$mymap2<- renderLeaflet({
        myMap <- leaflet()%>% #filters our data based on dropdown selection
            addProviderTiles(providers$CartoDB.Positron)%>% #adds a basemap
            addPolylines(data = weatherShapeData(), color = "Gray")%>%
            addCircleMarkers(data = sliderChoice(),
                             ~Long, ~Lat,
                             radius = sliderChoice()$rides/1100,
                             label = ~STATION_DESCRIPTIVE_NAME)
    })
    
}

# Run the application 
shinyApp(ui = ui, server = server)
