const functions = require('firebase-functions');
const jwt = require('jsonwebtoken');
const keyData = require('./service_account.json');
//const request = require('request-promise');

function getAccessToken(keyData) {
  // Create a JSON Web Token for the Service Account linked to Play Store
  const token = jwt.sign(
    { scope: 'https://www.googleapis.com/auth/androidpublisher' },
    keyData.private_key,
    {
      algorithm: 'RS256',
      expiresIn: '1h',
      issuer: keyData.client_email,
      subject: keyData.client_email,
      audience: 'https://www.googleapis.com/oauth2/v4/token'
    }
  );

  // Make a request to Google APIs OAuth backend to exchange it for an access token
  // Returns a promise
  return https.request.post({
    uri: 'https://www.googleapis.com/oauth2/v4/token',
    form: {
      'grant_type': 'urn:ietf:params:oauth:grant-type:jwt-bearer',
      'assertion': token
    },
    transform: body => JSON.parse(body).access_token
  });
}

/**
 * Makes a GET request to given URL with the access token
*/
function makeApiRequest(url, accessToken) {
  return https.request.post({
    url: url,
    auth: {
      bearer: accessToken
    },
    //transform: body => JSON.parse(body)
    headers: {
      "Content-Type": "application/json"
    },
    body: {
    "instances": [
    [4,1,0,4,0,1,3,4,4,3,3,2,3,0,1,0,0,1,0,3],
    [0,3,3,0,4,3,0,1,0,0,0,0,1,3,3,3,4,3,3,1]
    ]
    }
  });
}

// Our test function
exports.testApi = functions.https.onCall((req, res) => {
  // TODO: process the request, extract parameters, authenticate the user etc

  // The API url to call - edit this
  const url = `https://ml.googleapis.com/v1/projects/cs506finalproject/models/most_important_issue/versions/mostimportantissuev1:predict`;

  getAccessToken(keyData)
    .then(token => {
      return makeApiRequest(url, token);
    })
    .then(response => {
      // TODO: process the response, e.g. validate the purchase, set access claims to the user etc.
      res.send(response);
      print("I am here");
      return;
    })
    .catch(err => {
      res.status(500).send(err);
    });
});
//const admin = require('firebase-admin');
//admin.initializeApp();


//const express = require('express');
//const app = express();
/*const authenticate = async (req, res, next) => {
  if (!req.headers.authorization || !req.headers.authorization.startsWith('Bearer ')) {
    res.status(403).send('Unauthorized');
    return;
  }
  const idToken = req.headers.authorization.split('Bearer ')[1];
  try {
    const decodedIdToken = await admin.auth().verifyIdToken(idToken);
    req.user = decodedIdToken;
    next();
    return;
  } catch(e) {
    res.status(403).send('Unauthorized');
    return;
  }
};

app.use(authenticate);
*/
	// // Create and Deploy Your First Cloud Functions
 // https://firebase.google.com/docs/functions/write-firebase-functions

   exports.hello = functions.https.onRequest((request, response) => {
   response.send("Hello from Firebase!");
});

// Take the text parameter passed to this HTTP endpoint and insert it into the
// Realtime Database under the path /messages/:pushId/original
exports.addMessage = functions.https.onRequest((req, res) => {
  // Grab the text parameter.
  const original = req.query.text;
  // Push the new message into the Realtime Database using the Firebase Admin SDK.
  return admin.database().ref('/messages').push({original: original}).then((snapshot) => {
    // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
    return res.redirect(303, snapshot.ref.toString());
  });
});

exports.getIssue = functions.https.onRequest((req, res) => {
   //print("hello world");
   // const mostImportantIssue
////////////////////////////////////////////////////////////////////////////
// Imports the Google Cloud client library.
//const {Storage} = require('@google-cloud/storage');
/*
// Instantiates  client. Explicitly use service account credentials by
// specifying the private key file. All clients in google-cloud-node have this
// helper, see https://github.com/GoogleCloudPlatform/google-cloud-node/blob/master/docs/authentication.md
const storage = new Storage({
  projectId: 'cs506finalproject',
  keyFilename: 'service_account.json',
});

// Makes an authenticated API request.
storage
  .getBuckets()
  .then(results => {
    const buckets = results[0];

    console.log('Buckets:');
    buckets.forEach(bucket => {
      console.log(bucket.name);
    });
    res.status(200);
  })
  .catch(err => {
    console.error('ERROR:', err);
  });
  */
  bucketName = 'my-new-bucket'; // The name for the new bucket
  // [START storage_quickstart]
  // Imports the Google Cloud client library
  const {Storage} = require('@google-cloud/storage');

  // Creates a client
  const storage = new Storage();

  /**
   * TODO(developer): Uncomment these variables before running the sample.
   */
  // const bucketName = 'bucket-name';
  createBucket();
});

//app.api = functions.https.onRequest(app);
