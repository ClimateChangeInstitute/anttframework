###########################################
#File SaundersIsland.R
##Version 1
# #  Andrei Kurbatov
# #  Tyler Sullivan
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
	pdfFileName="~/Desktop/SaundersIsland.pdf"
	} else {pdfFileName="~/Sites/AntT/Data/rplots/SaundersIsland.pdf"}

siteName="Saunders Island        "
regionName="South Sandwich Islands"
plotSubtitle="Analyses recalculated to 100% volatile-free"
#Adjust plot size by changing parameters on teh next line
if(plotToPDF){pdf(pdfFileName, width=9, height=7)}
#####################################END PDF FILE####
##TAS Diagram plots
ifelse(Datafromserver,
	source("http://www.tephrochronology.org/AntT/Data/rscripts/TASdiagram.R"),
	source("~/Sites/AntT/Data/rscripts/TASdiagram.R")
	)

#plot TAS diagram, using function from kurbatovRfuctions.R file
TASdiagram(TRUE, plotSubtitle,"lightblue","lightblue")

##############################PLOT BULK CHEMISTRY DATA
#Location of ascii comma separated Major Elements ICPMS bulk chemistry data file 

ifelse(Datafromserver,
	url<-"http://www.tephrochronology.org/AntT/Data/rdata/SaundersIsland.csv",
	url<-"~/Sites/AntT/Data/rdata/SaundersIsland.csv"
	)
#Number of lines with data 7-4 =3
Nlines = 3
#Number of lines to skip
Nskip = 3
#Read data file
chem<-read.table(url,skip=Nskip,sep=",", fill=TRUE, header = TRUE, na.strings = "-99", nrow=Nlines)


#add samples to the plot using ADD2TAS function storted in TASdiagram.R file
ADD2TAS(chem, Nlines, DisplayLegend)
#
#turn off PDF file
if(plotToPDF){dev.off()
	system(paste("open", pdfFileName))
}