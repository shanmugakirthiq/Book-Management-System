import React, { useState } from "react";
import { Box, TextField, Button, Typography, Paper, Link } from "@mui/material";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";

const MemberLogin = () => {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({ email: "", password: "" });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    setError("");

    if (!credentials.email || !credentials.password) {
      setError("Both fields are required!");
      return;
    }

    setLoading(true);

    try {
      const response = await fetch("http://localhost:8080/member/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include", 
        body: JSON.stringify(credentials),
      });

      if (response.ok) {
        const result = await response.text();
        if (result === "FIRST_TIME") {
          navigate("/change-password");  // Redirect to ChangePassword page
        } else {
          sessionStorage.setItem("memberSession", "true"); 
          navigate("/member-dashboard");
        }
      } else {
        const result = await response.text();
        setError(result);
      }
    } catch (error) {
      setError("Something went wrong. Try again later.");
    } finally {
      setLoading(false);
    }
};

  return (
    <Box
      sx={{
        width: "100vw",
        height: "100vh",
        backgroundImage: "url('https://images.pexels.com/photos/590493/pexels-photo-590493.jpeg')",
        backgroundSize: "cover",
        backgroundPosition: "center",
        position: "relative",
      }}
    >
      <Box
        sx={{
          position: "absolute",
          width: "100%",
          height: "100%",
          backgroundColor: "rgba(0, 0, 0, 0.6)",
        }}
      />

      <Navbar />
      <Box
        sx={{
          position: "absolute",
          top: "50%",
          right: "5%",
          transform: "translateY(-50%)",
          maxWidth: 380,
          width: "100%",
        }}
      >
        <Paper
          elevation={10}
          sx={{
            p: 4,
            textAlign: "center",
            background: "rgba(255, 255, 255, 0.9)",
            borderRadius: 3,
            boxShadow: "10px 10px 30px rgba(0,0,0,0.2)",
          }}
        >
          <Typography variant="h5" sx={{ fontWeight: "bold", color: "#333", mb: 2 }}>
            Member Login
          </Typography>

          <TextField
            fullWidth
            label="Email"
            variant="outlined"
            value={credentials.email}
            onChange={(e) => setCredentials({ ...credentials, email: e.target.value })}
            sx={{ mb: 2, backgroundColor: "#fff", borderRadius: "5px" }}
          />

          <TextField
            fullWidth
            type="password"
            label="Password"
            variant="outlined"
            value={credentials.password}
            onChange={(e) => setCredentials({ ...credentials, password: e.target.value })}
            sx={{ mb: 2, backgroundColor: "#fff", borderRadius: "5px" }}
          />

          {error && (
            <Typography color="error" sx={{ mb: 2, fontSize: "0.9rem" }}>
              {error}
            </Typography>
          )}

          <Button
            variant="contained"
            fullWidth
            onClick={handleLogin}
            disabled={loading}
            sx={{
              py: 1.2,
              fontSize: "1rem",
              borderRadius: "25px",
              textTransform: "none",
              background: "linear-gradient(135deg, #1e3c72, #2a5298)",
              transition: "0.3s",
              "&:hover": {
                background: "linear-gradient(135deg, #2a5298, #1e3c72)",
                transform: "scale(1.05)",
              },
            }}
          >
            {loading ? "Logging in..." : "Login"}
          </Button>

          <Typography sx={{ mt: 2, fontSize: "0.9rem" }}>
            New to BMS?{" "}
            <Link
              component="button"
              onClick={() => navigate("/member-register")}
              sx={{ color: "#2a5298", fontWeight: "bold", textDecoration: "none" }}
            >
              Register now
            </Link>
          </Typography>
        </Paper>
      </Box>
    </Box>
  );
};

export default MemberLogin;
