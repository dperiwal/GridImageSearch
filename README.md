GridImageSearch
===============
by Damodar Periwal

<b>The following user stories have been completed:</b>

- User can enter a search query that will display a grid of image results from the Google Image API.
- User can click on "settings" which allows selection of advanced search options to filter results
- User can configure advanced search filters such as: 
      - Size (small, medium, large, extra-large)
      - Color filter (black, blue, brown, gray, green, etc...)
      - Type (faces, photo, clip art, line art)
      - Site (yahoo.com)

-	Subsequent searches will have any filters applied to the search results
- User can tap on any image in results to see the image full-screen
- User can scroll down “infinitely” to continue loading more image results (up to 8 pages)


<b>The following advanced optional user stories have been completed:</b>
- Advanced: Robust error handling, check if internet is available, handle error cases, network failures
- Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
- Advanced: User can share an image to their friends or email it to themselves
- Advanced: Replace Filter Settings Activity with a lightweight modal overlay
- Added a Cancel button in the Filter Setting Fragment to easily dismiss the operation without changing anything.

  
<b>Issue:</b> 
Sharing feature is not working properly in the emulator but works fine on a device.

<b>Third-party libraries:</b>
The following third party libraries are used in this project under their respective licenses:

     android-async-http-1.4.6.jar
     picasso-2.3.4.jar
     
     

Walkthrough of all user stories:

![Animated Walkthrough](DamodarGridImageSearch2.gif "Animation that shows the working of the app in an emulator")

The file DamodarGridImageSearch1.gif shows the working of an earlier version of the app in an emulator.

GIF created with [LiceCap](http://www.cockos.com/licecap/).


