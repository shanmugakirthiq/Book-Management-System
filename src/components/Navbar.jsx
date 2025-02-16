import React from "react";
import { AppBar, Toolbar, Typography, Button, Box, IconButton } from "@mui/material";
import MenuBookIcon from "@mui/icons-material/MenuBook";
import { useNavigate } from "react-router-dom";

const Navbar = () => {
  const navigate = useNavigate();

  return (
    <AppBar
      position="absolute"
      sx={{
        backgroundColor: "rgba(0, 0, 0, 0.3)",
        boxShadow: "none",
        backdropFilter: "blur(5px)",
        zIndex: 3,
        px: 2,
      }}
    >
      <Toolbar>
        <IconButton edge="start" onClick={() => navigate("/")}>
          <MenuBookIcon sx={{ fontSize: 30, color: "#fff" }} />
        </IconButton>
        <Typography variant="h6" sx={{ flexGrow: 1, fontWeight: "bold", color: "#fff" }}>
          Book Management
        </Typography>
        <Box>
          <Button
            onClick={() => navigate("/")}
            sx={{ color: "#fff", textTransform: "none", fontWeight: "bold" }}
          >
            Home
          </Button>
          <Button
            onClick={() => navigate("/admin-login")}
            sx={{ color: "#fff", textTransform: "none", fontWeight: "bold", ml: 2 }}
          >
            Admin
          </Button>
          <Button
            onClick={() => navigate("/member-login")}
            sx={{ color: "#fff", textTransform: "none", fontWeight: "bold", ml: 2 }}
          >
            Member
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
