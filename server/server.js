require("dotenv").config();
const express = require("express");
const jwt = require("jsonwebtoken");
const cors = require("cors");
const axios = require("axios");

const app = express();
app.use(cors());
app.use(express.json());

const GOOGLE_CLIENT_ID = "777199134518-ese8nqoo5upv6ev1j1frir7mj6r73o5k.apps.googleusercontent.com";

async function verifyGoogleToken(idToken) {
    const response = await axios.get(
        `https://oauth2.googleapis.com/tokeninfo?id_token=${idToken}`
    );
  //  if (response.data.aud !== GOOGLE_CLIENT_ID) {
    //    throw new Error("Invalid Token");
    //}

    console.log(response.data.aud)
    return response.data;
}

app.post("/auth/google",  (req, res) => {
    try {
        const { idToken } = req.body;
        
        const googleUser =  verifyGoogleToken(idToken);
        console.log(googleUser.email)

        console.log(idToken)

        console.log("process.env.JWT_SECRET")

        // Generate JWT for your app
        const appToken = jwt.sign(
            { email: googleUser.email, name: googleUser.name },
            process.env.JWT_SECRET,
            { expiresIn: "1h" }
        );

        res.json({ token: appToken, user: googleUser });
    } catch (error) {

        res.status(400).json({ error: error.message });
    }
});

app.listen(3000, () => console.log("Server running on port 3000"));
