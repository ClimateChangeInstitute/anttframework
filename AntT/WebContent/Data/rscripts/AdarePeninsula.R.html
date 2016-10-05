###########################################
#File AdarePeninsula.R
##Version 1
# #  Andrei Kurbatov
## July 2013
## TAS plot
##Line below turns on /off the legend
DisplayLegend=TRUE
#use for final plots
Datafromserver= TRUE
#####################################PDF FILE####
#Make it TRUE for PDF file generation
#DO not forget add at the end dev(off)
plotToPDF = TRUE
#name for output pdf file
if(Datafromserver) {
	pdfFileName="~/Desktop/AdarePeninsula.pdf"
	} else {pdfFileName="~/Sites/AntT/Data/rplots/AdarePeninsula.pdf"}
siteName="Adare Peninsula"
regionName="McMurdo Volcanic group"
#Adjust plot size by changing parameters on teh next line
if(plotToPDF){pdf(pdfFileName, width=9, height=7)}
#####################################END PDF FILE####
##TAS Diagram plots
ifelse(Datafromserver,
	source("http://www.tephrochronology.org/AntT/Data/rscripts/TASdiagram.R"),
	source("~/Sites/AntT/Data/rscripts/TASdiagram.R")
	)

#plot TAS diagram, using function from kurbatovRfuctions.R file
TASdiagram(TRUE, "Analyses recalculated to 100% volatile-free","lightblue","lightblue")

##############################PLOT BULK CHEMISTRY DATA
#Location of ascii comma separated Major Elements ICPMS bulk chemistry data file 

ifelse(Datafromserver,
	url<-"http://www.tephrochronology.org/AntT/Data/rdata/AdarePeninsula.csv",
	url<-"~/Sites/AntT/Data/rdata/AdarePeninsula.csv"
	)
#Number of lines with data 7-4 =3
Nlines = 3
#Number of lines to skip
Nskip = 3
#Read data file
chem<-read.table(url,skip=Nskip,sep=",", fill=TRUE, header = TRUE, nrow=Nlines)


#add samples to the plot
points((chem$SiO2[1:Nlines]*100)/(chem$Total[1:Nlines]- chem$LOI[1:Nlines]), (chem$Na2O[1:Nlines]*100)/(chem$Total[1:Nlines]- chem$LOI[1:Nlines])+ (chem$K2O[1:Nlines]*100)/(chem$Total[1:Nlines]- chem$LOI[1:Nlines]), col="red",pch=2)

#
#################################################################################
#add legend for bulk rocks to the plot

#PLOT COLOR LEGEND information
#text(66.3,16,"", col="blue")

if(DisplayLegend){
#################################################################################
#Plot legend for samples
###########LEGEND###########################
legend("topleft", cex=1.3,
# bty="n",#No frame
 legend=c(
"", #"Bransfield Strait volcanoes",#subtitle
siteName
),
col=c(
"red"),
 pch=c(NA_integer_,2
  ), #Symbols for the legend
 bg="aliceblue",
 #title = "Bulk rock data"
 )
text(35,16,regionName,adj=0, font=2)

 } #end  Display Legend
###########END LEGEND ###########################
#turn off PDF file
if(plotToPDF){dev.off()
	system(paste("open", pdfFileName))
}