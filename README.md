This project was code contributed by several people at a Here.com hackathon in Cambridge MA

Contributors included:
Frank Kelly,
Shane Detsch,
Sri Rajagopalan,
Naeem Tahir
(and probably some other folks I am forgetting)

The goal for "Best Day Ever" was a mobile application that would provide suggestions on things to do without necessarily understanding initially what you like or do not like (but which would learn over time)

To do this we needed

1) To know your location to figure out what places are nearby (using Here.com Places API)

2) What's the current weather today
 - Saturday might be a good day to go to the beach - but not if it's 15 F outside :-)

3) What's your schedule look like today - do you have 1 hour to kill (lunch date?) or 3 hours (visit a museum?) or a whole day (go to the beach?)

So we naturally have 3 components to integrate with

1) Places API

2) Weather API - we did REST API calls against https://api.forecast.io

3) Your Schedule in Google calendar

The "Engine" component would then integrate all of these pieces using a Rules Engine (e.g. Drools / JESS) to put together this informatin and make "smart" suggestions based on common sense rules.


In the end we got the back-end mostly working :-) 
And for our hard-work, creativity and innovatin we claimed 3rd place (Yeah!)



