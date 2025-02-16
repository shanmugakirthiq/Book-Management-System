import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AppBar, Toolbar, Typography, Avatar, Box, Button } from "@mui/material";
import LogoutIcon from "@mui/icons-material/Logout";

const Topbar = () => {
  const navigate = useNavigate();
  const [adminName, setAdminName] = useState("");

  useEffect(() => {
    const fetchAdminDetails = async () => {
      try {
        const response = await fetch("http://localhost:8080/admin/details", {
          method: "GET",
          credentials: "include",
        });

        if (response.ok) {
          const adminData = await response.json();
          setAdminName(adminData.username); 
        } else {
          navigate("/admin-login");
        }
      } catch (error) {
        console.error("Error fetching admin details:", error);
        navigate("/admin-login");
      }
    };

    fetchAdminDetails();
  }, [navigate]);

  const handleLogout = async () => {
    try {
      const response = await fetch("http://localhost:8080/admin/logout", {
        method: "POST",
        credentials: "include",
      });

      if (response.ok) {
        navigate("/admin-login");
      } else {
        console.error("Logout failed");
      }
    } catch (error) {
      console.error("Error during logout:", error);
    }
  };

  return (
    <AppBar position="fixed" sx={{ backgroundColor: "#1976D2", zIndex: 1201 }}>
      <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
        <Typography variant="h6" fontWeight="bold">
          Book Management System
        </Typography>

        <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
          <Typography variant="subtitle1">{adminName || "Admin"}</Typography>
          <Avatar sx={{ bgcolor: "#fff", color: "#1976D2" }}>{adminName ? adminName[0].toUpperCase() : "A"}</Avatar>
          <Button
            variant="contained"
            color="secondary"
            startIcon={<LogoutIcon />}
            onClick={handleLogout}
            sx={{
              textTransform: "none",
              backgroundColor: "#D32F2F",
              "&:hover": { backgroundColor: "#B71C1C" },
            }}
          >
            Logout
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Topbar;
