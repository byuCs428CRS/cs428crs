###### Expect this script to take 1-2 minutes to fully run. It has to download 170 URLS. 
###### Run as "python ratemyprof.py" in the terminal. The output will be printed to "ratemyprof.json" in the same directory.
###### It overwrites the json file each time with the new data.
###### Author: Lexi Christiansen
###### For: BYU Class Registration (CS428)
###### Date Added: 18 March 2014

from sys import argv
import urllib2
import httplib
import json
import cookielib
import requests


class TeacherIDParser:
	def __init__(self):
		self.Rate_BYU_StartPage = "http://www.ratemyprofessors.com/SelectTeacher.jsp?the_dept=All&orderby=TLName&sid=135"
		self.jsonFormat = []
		self.jsonString = ""

	def parse(self):
		i = 1
		while(1):
			page = "http://www.ratemyprofessors.com/SelectTeacher.jsp?the_dept=All&orderby=TLName&sid=135&pageNo="
			r = requests.get(page + str(i))
			if "Page Not Found!" in r.text:
				break
			self.parseHTML(r.text)
			i += 1	
		self.jsonString = json.dumps(self.jsonFormat)
		

	def parseHTML(self, html):
		list1 = html.split("<div class=\"profName\"><a href=\"ShowRatings.jsp?tid=")
		
		#remove first piece
		del list1[0]

		for i in range(len(list1)):
			list2 = list1[i].split("\">")
			teacherID = list2[0]
			list3 = list2[1].split("<")
			teacherName = list3[0]
			teacherObject = {}
			teacherObject["teacherID"] = teacherID
			teacherObject["teacherName"] = teacherName
			self.jsonFormat.append(teacherObject)
	
	def printJson(self):
		print self.jsonString

	def writeJsonToFile(self):
		with open("ratemyprof.json", "w") as myfile:
			myfile.write(self.jsonString)


if __name__ == "__main__":
	parser = TeacherIDParser()
	parser.parse()
	parser.writeJsonToFile()
