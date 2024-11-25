#weather spreadsheet
#goals:
#extract everything properly
#combine with the original dataset, 2022 to 2023
#plots

library(ggplot2)
library(here)
library(tidyverse)
library(lubridate)
library(ggpubr)

#datasets
dailyTotals <- read.csv("ctaLDailyTotals.csv")
#very large, takes some time to instantiate. 
typeTotals <- read.csv("ctaLMonthlyTotals.csv")
stops <- read.csv("ctaStops.csv")
weather <- read.csv("dayAverageChicagoWeather.csv", skip = 9)

#weather row names
colnames(weather) <- c('date','maxTemp','minTemp','meanTemp','sumPrecip','sumSnow')

#format dates
dailyTotals$date = mdy(dailyTotals$date)
#Decode dayType column
dailyTotals$daytype[dailyTotals$daytype=="W"] <- "Weekday"
dailyTotals$daytype[dailyTotals$daytype=="A"] <- "Saturday"
dailyTotals$daytype[dailyTotals$daytype=="U"] <- "Sunday/Holiday"
#create year, month, and day variables
dailyTotals%>%
  mutate(year = format(as.Date(dailyTotals$date, format="%d/%m/%Y"),"%Y"))%>%
  mutate(month = format(as.Date(dailyTotals$date, format="%d/%m/%Y"),"%m"))%>%
  mutate(day = format(as.Date(dailyTotals$date, format="%d/%m/%Y"),"%d"))

#filter for 2022/2023
dailyTotals <- dailyTotals[order(as.Date(dailyTotals$date, format="%m/%d/%Y")),]
dailyTotals <- dailyTotals %>%
  filter(date >= '2022-01-01')

#cleaning weather dataset
str(weather)
weather$date <- substr(weather$date, 1, nchar(weather$date)-5)
weather$date <- ymd(weather$date)

#filter dataset
weather <- weather %>%
  filter(date <= '2023-06-30')

#left join attempt
joinedData <- left_join(dailyTotals, weather, by='date')
#WOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
#Create list of red line stops
stops%>%
  filter(RED == "true")%>%
  rename(station_id = MAP_ID)%>%
  select(station_id) -> redLineStops
redLineStops

#temperature range visualization
joinedData <- joinedData%>%
  mutate(year = format(as.Date(joinedData$date, format="%d/%m/%Y"),"%Y"))%>%
  mutate(month = format(as.Date(joinedData$date, format="%d/%m/%Y"),"%m"))%>%
  mutate(day = format(as.Date(joinedData$date, format="%d/%m/%Y"),"%d"))

joinedData%>%
  filter(year == 2022) %>%
  ggplot(aes(x = month, y = meanTemp)) + geom_line(aes(color = meanTemp)) +
  scale_color_gradient(low = "darkviolet", high = "red") + theme_grey() +
  labs(x = 'Months of 2022', y = 'Range of Daily Temperatures (F)', 
       title = '2022 Recorded Temperature Ranges')

#ranges in ridership for a specific station
joinedData %>%
  filter(year == 2022 & station_id == 41220) %>%
  ggplot(aes(x = month, y = rides)) + geom_line(aes(color = rides)) +
  scale_color_gradient(low = 'darkolivegreen1', high = "green") + theme_dark() +
  labs(x = 'Months of 2022', y = 'Range of Daily Ridership', 
       title = '2022 Fullerton Ridership Ranges per Month')

joinedData %>%
  filter(year == 2022 & month == 11 & station_id == 41220) %>%
  ggplot(aes(x = day, y = rides)) + geom_point() +
  theme_grey() +
  labs(x = 'Days of November 2022', y = 'Ridership', 
       title = 'November 2022 Fullerton Ridership Daily Totals')


#write data
write.csv(joinedData, here("joinedData.csv"))

#common legend output
#ranges in ridership for a specific station
Q1Plot <- joinedData %>%
  filter(year == 2022 & station_id == 40900) %>%
  ggplot(aes(x = month, y = rides)) + geom_line(aes(color = rides)) +
  scale_color_gradient(low = 'darkolivegreen1', high = "green") + theme_dark() +
  labs(x = 'Months of 2022', y = 'Ridership', 
       title = 'Howard')

Q2Plot <- joinedData %>%
  filter(year == 2022 & station_id == 40560) %>%
  ggplot(aes(x = month, y = rides)) + geom_line(aes(color = rides)) +
  scale_color_gradient(low = 'darkolivegreen1', high = "green") + theme_dark() +
  labs(x = 'Months of 2022', y = 'Ridership', 
       title = 'Jackson')

Q3Plot <- joinedData %>%
  filter(year == 2022 & station_id == 41420) %>%
  ggplot(aes(x = month, y = rides)) + geom_line(aes(color = rides)) +
  scale_color_gradient(low = 'darkolivegreen1', high = "green") + theme_dark() +
  labs(x = 'Months of 2022', y = 'Ridership', 
       title = 'Addison-North Main')

Q4Plot <- joinedData %>%
  filter(year == 2022 & station_id == 41000) %>%
  ggplot(aes(x = month, y = rides)) + geom_line(aes(color = rides)) +
  scale_color_gradient(low = 'darkolivegreen1', high = "green") + theme_dark() +
  labs(x = 'Months of 2022', y = 'Ridership', 
       title = 'Cermak-Chinatown')

combinedPlot <- ggarrange(Q1Plot, Q2Plot, Q3Plot, Q4Plot, 
          common.legend = FALSE, legend = "right") 

annotate_figure(combinedPlot, top = text_grob("Ridership Ranges for Red Line Stations 2022", 
                                     color = "skyblue", face = "bold", size = 14))

#HOMEWORK 4
#2d binning for numerical variable relationships, problem 1
joinedData %>%
  ggplot(aes(x = meanTemp, y = rides)) + geom_bin2d(bins = 40) + theme_bw() + 
  scale_fill_continuous(type = "viridis") +
  labs(x = 'Average Daily Temperature', y = 'Single Station Daily Ridership', 
       title = 'Density Plot of Daily Temperatures and Rides')

#special timeseries plots/tile plots, problem 2, during lollapalooza.
joinedData %>%
  ggplot(aes(x = (sumSnow + sumPrecip), y = rides)) + 
    stat_bin_2d(bins = c(10,30)) + scale_fill_continuous(type = 'viridis') +
  labs(x = 'Daily Precipitation/Snowfall (inches)', 
       y = 'Single Station Daily Ridership', 
       title = 'All Precipitation to Daily Ridership')




