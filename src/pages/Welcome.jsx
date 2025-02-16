import React from "react";
import { Box, Container, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";

const Welcome = () => {
  const navigate = useNavigate();

  return (
    <Box
      sx={{
        position: "relative",
        width: "100vw",
        height: "100vh",
        backgroundImage: "url('https://collegeinfogeek.com/wp-content/uploads/2018/11/Essential-Books.jpg')",
        backgroundSize: "cover",
        backgroundPosition: "center",
      }}
    >
      <Box
        sx={{
          position: "absolute",
          width: "100%",
          height: "100%",
          backgroundColor: "rgba(0, 0, 0, 0.5)",
          zIndex: 1,
        }}
      />

      <Navbar />

      <Box
        sx={{
          position: "relative",
          zIndex: 2,
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          height: "100%",
        }}
      >
        <Container maxWidth="md">
          <Box
            sx={{
              p: 4,
              textAlign: "center",
              background: "rgba(255, 255, 255, 0.2)",
              backdropFilter: "blur(10px)",
              borderRadius: "16px",
              boxShadow: "0 8px 32px 0 rgba(31, 38, 135, 0.37)",
              border: "1px solid rgba(255, 255, 255, 0.18)",
            }}
          >
            <Typography
              variant="h4"
              gutterBottom
              sx={{
                fontWeight: "bold",
                color: "#fff",
                textShadow: "0px 0px 10px rgba(0,0,0,0.5)",
              }}
            >
              Welcome to Book Management System
            </Typography>
            <Typography
              variant="body1"
              gutterBottom
              sx={{
                color: "#ddd",
                mb: 4,
                textShadow: "0px 0px 10px rgba(0,0,0,0.5)",
              }}
            >
              Efficiently manage your books, members, and rentals with our seamless system.
            </Typography>
            <Box sx={{ display: "flex", justifyContent: "center", gap: 2 }}>
              <Button
                variant="contained"
                color="primary"
                onClick={() => navigate("/admin-login")}
                sx={{ px: 3, py: 1.5, textTransform: "none", fontSize: "1rem" }}
              >
                Admin Login
              </Button>
              <Button
                variant="outlined"
                color="secondary"
                onClick={() => navigate("/member-login")}
                sx={{
                  px: 3,
                  py: 1.5,
                  textTransform: "none",
                  fontSize: "1rem",
                  borderColor: "#fff",
                  color: "#fff",
                }}
              >
                Member Login
              </Button>
            </Box>
          </Box>
        </Container>
      </Box>
    </Box>
  );
};

export default Welcome;
