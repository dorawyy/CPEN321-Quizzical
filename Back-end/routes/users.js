let express = require("express");
let MongoClient = require("mongodb").MongoClient;
let fs = require("fs");
let path = require("path");
let router = express.Router();
let db;

MongoClient.connect(
  "mongodb://localhost:27017",
  {useUnifiedTopology: true},
  (err, client) => {
    db = client.db("data");
  }
);


router.get("/", (req, res, next) => {
  let url = new URL(req.originalUrl, `http://${req.headers.host}`);
  let uid = url.searchParams.get("userId");
  let type = url.searchParams.get("type");

  let timeout = 2000;

  if (type === null) {
    db.collection("userInfo").find({ uid: { $eq: uid }}).project({profileImage:0, _id:0}).maxTimeMS(timeout).toArray((err,data) => {
      res.send(data);
    });
  }
  else if (type === "userInfo") {
    db.collection("userInfo").find({ uid: { $eq: uid }}).project({_id:0}).maxTimeMS(timeout).toArray((err,data) => {
      res.send(data);
    });
  }
  else {
    res.send("request invalid");
  }
});

router.get("/contact", (req, res, next) => {
  let url = new URL(req.originalUrl, `http://${req.headers.host}`);
  let uid = url.searchParams.get("userId");
  let type = url.searchParams.get("type");

  let timeout = 2000;

  if (type === "username") {
    db.collection("userInfo").find({ uid: { $eq: uid }}).project({username:1, _id:0}).maxTimeMS(timeout).toArray((err, username) => {
      username = Object.values(username[0])[0];
      res.send(""+username);
    });
  }
  else if (type === "Email") {
    db.collection("userInfo").find({ uid: { $eq: uid }}).project({Email:1, _id:0}).maxTimeMS(timeout).toArray((err, email) => {
      email = Object.values(email[0])[0];
      res.send(""+email);
    });
  }
  else {
    res.send("request invalid");
  }
});

router.get("/classList", (req, res, next) => {
  let url = new URL(req.originalUrl, `http://${req.headers.host}`);
  let uid = url.searchParams.get("userId");
  db.collection("userInfo").find({uid: {$eq: uid}}).project({classList:1, _id:0})
  .toArray((err, classList) => {
    classList = Object.values(classList[0])[0];
    res.send("" + classList);
  });
});

router.get("/classDetails", (req, res, next) => {
  let url = new URL(req.originalUrl, `http://${req.headers.host}`);
  let uid = url.searchParams.get("userId");
  let type = url.searchParams.get("type");

  let timeout = 2000;

  if (type === "isInstructor") {
    db.collection("userInfo").find({ uid: { $eq: uid }}).project({isInstructor:1, _id:0}).maxTimeMS(timeout).toArray((err, isInstructor) => {
      isInstructor = Object.values(isInstructor[0])[0];
      res.send(""+isInstructor);
    });
  } else {
    res.send("request invalid");
  }
});

router.get("/classStats", (req, res, next) => {
  let url = new URL(req.originalUrl, `http://${req.headers.host}`);
  let uid = url.searchParams.get("userId");
  let type = url.searchParams.get("type");

  let timeout = 2000;

  if (type === "EXP") {
    db.collection("userInfo").find({ uid: { $eq: uid }}).project({EXP:1, _id:0}).maxTimeMS(timeout).toArray((err, exp) => {
      exp = Object.values(exp[0])[0];
      res.send(""+exp);
    });
  }
  else if (type === "userQuizCount") {
    db.collection("userInfo").find({ uid: { $eq: uid }}).project({userQuizCount:1, _id:0}).maxTimeMS(timeout).toArray((err, quizCount) => {
      quizCount = Object.values(quizCount[0])[0];
      res.send(""+quizCount);
    });
  }
  else {
    res.send("request invalid");
  }
});

router.get("/notifications", (req, res, next) => {
  let url = new URL(req.originalUrl, `http://${req.headers.host}`);
  let uid = url.searchParams.get("userId");
  let type = url.searchParams.get("type");

  let timeout = 2000;

  if (type === "notificationFrequency") {
    db.collection("notificationFrequency").find({ uid: { $eq: uid }}).project({notificationFrequency:1, _id:0}).maxTimeMS(timeout).toArray((err, frequency) => {
      frequency = Object.values(frequency[0])[0];
      res.send(""+frequency);
    });
  }
  else {
    res.send("request invalid");
  }
});

/* GET users listing. */
router.get("/profile", (req, res, next) => {
  let url = new URL(req.originalUrl, `http://${req.headers.host}`);
  let uid = url.searchParams.get("userId");
  let type = url.searchParams.get("type");

  if (type === "profileImage") {
    const filepath = "images/"+uid+"/profile_img.jpg";
    let string = "";
    if (fs.existsSync(filepath)) {
      let bitmap = fs.readFileSync(filepath);
      string = Buffer(bitmap).toString("base64");
    }
    res.send(string);
  }
  else {
    res.send("request invalid");
  }
});

module.exports = router;
