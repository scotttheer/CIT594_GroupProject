# CIT594_GroupProject
4/15 Updates
- Added logger class, added logging functionality to main and analyzer classes
- Updated Main class to run UI, updated UI class
I believe we have two things left to finish and then we are done:
  1) Get the csv reader to work on the comma issue
  2) Add "memoization" wrapper to each of the 6 methods in Analyzer

Scott

4/14 Updates
- Completed a first draft of the code's Main and UI classes. Haven't done the logger classes yet. I updated a few part of your code to integrate with the Main class:
  1) Reader classes take in a file instead of a string filename (easier for me from the Main)
  2) I moved the population, allproperties, violations, etc. classes into the data package because I'm now accessing them straight in Main
  3) I now instantiate the data classes in Main to pass them to the reader and then processor classes (so that they are the same instances that are updated in your reader classes)

Haven't yet taken a look at the csv reader comma issue, but we should touch base on that again soon. Will continue to work on the logger and will update here when I finish. Thanks for getting the code done so quickly!!!

Scott
  
- We can share updates on progress here! ; Scott
