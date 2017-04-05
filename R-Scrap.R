
library(XML)
library(tcltk2)

webScrap <- function() {
  
  tbls_xml <- readHTMLTable("http://www.moneycontrol.com/terminal/index_v1.php", which = c(2))
  
  head(tbls_xml)
  write.csv(tbls_xml, file = "stock.csv", row.names = TRUE)
  
}

tclTaskSchedule(10000, webScrap(), id = "webScrap", redo = TRUE)

tclTaskDelete("webScrap")

