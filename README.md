# Mapillary Java API

![Image](https://github.com/it-open/mapillary-api/raw/master/doc/graphics.png)

Mapillay API as Java Library see https://www.mapillary.com/developer/api-documentation/
This Project should wrap the complete API with Java Functions and Objects.

It is Work in Progress.
## What Is Implemented? ##
* OAuth authentication with ClientID (Custom ClientID, OAuth Token Link possible). For this we need a Webserver to get the Token. Everything is built in and should work out of the Box. If you have an external Token you can skip this step and just work with the API.
* Download Sequences
* Download Image Metadata
* Users and Statistcs
* Pagination (50%)
* Create UploadSequence (New, What sequences are open, delete and publish)
* Example Code

## What to Come ##
* image Upload
* Everything Else: Changeset, Map Features, Object Detection 
* Documentation
Just give me time to implement it or help.

## How to use it ##

First of all lets make a big Mapillary Object
```java
 Mapillary m = new Mapillary();
```
On the Mapillary Page - Dashboard - Developers
Create a new App. Name it your way. For the Callback URL use 'http://localhost:9876/token'. 
Why? When you make an OAuth. The Mapillary OAuth system will return an Token back to the Programm. 
Thats why we habe to start an Webserver and recieve the Token. This is all done in here, but you can build your own System as well.
Also Allow the Upload (Just in case you want to)

So lets do it with the Library and start an Authentication process. (The User has to login on the Mapillary Project Page to authenicate the App)
```java
m.setClientID("UzZRbjZEUm1jNGFsNi1CS3g3RjNydzo4MzcyMjAyODQwOGQ1M2Qy"); // Your Client Id
m.setRedirectUrl("http://localhost:9876/token"); // Not needed if its the same

m.startOAuthServer(null);
m.startOAuth(Mapillary.SCOPE.USER_WRITE, Mapillary.SCOPE.PRIVATE_UPLOAD, Mapillary.SCOPE.PUBLIC_UPLOAD); // start Server and open Browser
m.waitforAccess(50); // Wait 50 seconds to complete
```
Alternatively if you already have an AccessToken from somewhere (they rareley get Unvalid)
```java
// Set the ClientID as Well, Callback url is not neede
m.setAccess_token("4663gdffdgdUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJtcHkiLCJzdWIiOiJTNlFuNkRSbWM0YWw2LUJLeDdGM3J3IiwiYXVkIjoiVXpaUmJqWkVVbTFqTkdGc05pMUNTM2czUmpOeWR6bzRNemN5TWpBeU9EUXdPR1ExTTJReSIsImlhdCI6MTU5NDgwNTk4NzY0NCwianRpIjoiZGM1Zjk5YWEyMzE3OGQ5NTNhZGQ1MTQ0OGYyZmY1ZDEiLCJzY28iOlsidXNlcjp3cml0ZSIsInByaXZhdGU6dXBsb2FkIiwicHVibGljOnVwbG9hZCJdLCJ2ZXIiOjF9.3bxmfMDDcujUBp5QcgGC7bFmxxPTn33YC1GUgXKQkTs");
```
So lets Find out who you are
```java
User user = m.getMe();
```
Simple Isnt it?
Which sequences are from you?
```java

SequenceCollection coll = user.fetchSequences(m, null);

// or just 10 in a row
SequenceFilter sf = new SequenceFilter();
sf.setPerPage(10);
SequenceCollection coll = user.fetchSequences(m, sf);
```

Lets upload Some Images
```java
// Open an Upload Sequence
UploadSequence uploadSequence = m.startUpload();
// Or get one I already know
UploadSequence uploadSequence = m.getUpload("324sT61o9YShiUO3mnAvvl");

// The Part Uploading the Images is missing: HELP!!! 
// No Problem still in Progress can U wait?

uploadSequence.publish();
```

Yes I know its not finished. Im working on it.



