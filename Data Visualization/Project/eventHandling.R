#sorting through events
#packages
library(ggplot2)
library(here)
library(tidyverse)
library(lubridate)
library(ggpubr)

#july 2022!
#stations of interest:
#washington/wabash, adams/wabash, jackson/state red, monroe/state red, 
#jackson/dearborn blue, monroe/dearborn blue

#dataset
lollapalooza <- read.csv('lolladata.csv')
lollaStations <- c(40680, 41700, 40560, 41090, 40070, 40790)

#brown line
lollaBrown <- lollapalooza %>%
  filter(station_id == 40680 | station_id == 41700)
#remove x, station_id, station name
lollaBrown <- lollaBrown[, -c(1, 2, 3)]
#now to combine on ridership
lollaBrownRides <- lollaBrown %>%
  group_by(date) %>%
  summarise(across(rides, sum))

write.csv(lollaBrownRides, 'lollaBrownRides.csv')

#blue line
lollaBlue <- lollapalooza %>%
  filter(station_id == 40070 | station_id == 40790)
#remove x, station_id, station name
lollaBlue <- lollaBlue[, -c(1, 2, 3)]
#now to combine on ridership
lollaBlueRides <- lollaBlue %>%
  group_by(date) %>%
  summarise(across(rides, sum))

write.csv(lollaBlueRides, 'lollaBlueRides.csv')

#red line
lollaRed <- lollapalooza %>%
  filter(station_id == 40560 | station_id == 41090)
#remove x, station_id, station name
lollaRed <- lollaRed[, -c(1, 2, 3)]
#now to combine on ridership
lollaRedRides <- lollaRed %>%
  group_by(date) %>%
  summarise(across(rides, sum))

write.csv(lollaRedRides, 'lollaRedRides.csv')

#make a combined summed ridership dataset?
colnames(lollaBrownRides) <- c('date', 'Brown Line Rides')
colnames(lollaBlueRides) <- c('date', 'Blue Line Rides')
colnames(lollaRedRides) <- c('date', 'Red Line Rides')
lollaCombined <- cbind(lollaBrownRides, lollaBlueRides$`Blue Line Rides`, 
                       lollaRedRides$`Red Line Rides`)
colnames(lollaCombined) <- c('date', 'brownRides', 'blueRides', 'redRides')
write.csv(lollaCombined, 'lollaCombined.csv')

