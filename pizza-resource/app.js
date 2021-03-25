const express = require("express"),
  app = express(),
  formidable = require("formidable"),
  path = require("path"),
  fs = require("fs"),
  throttle = require("express-throttle-bandwidth");

const port = process.env.PORT || 4444,
  folder = path.join(__dirname, "static/images");

if (!fs.existsSync(folder)) {
  fs.mkdirSync(folder);
}
app.use(express.static("static"));

app.set("port", port);
app.use(throttle(1024 * 128)); // throttling bandwidth

app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept"
  );
  next();
});
app.get("/", (res) => {
  res.send("Test");
});

app.post("/upload", (req, res) => {
  const form = new formidable.IncomingForm();
  form.keepExtensions = true;
  form.uploadDir = folder;
  form
    .parse(req)
    .on("field", (name, field) => {
      console.log("Field", name, field);
    })
    .on("fileBegin", (name, file) => {
      console.log("fn: " + file.name);
      console.log("n: " + name);
      res.send( path.basename(file.path));
    })
    .on("end", () => {
      res.end();
    });
});

app.listen(port, () => {
  console.log("\nUpload server running on http://localhost:" + port);
});
