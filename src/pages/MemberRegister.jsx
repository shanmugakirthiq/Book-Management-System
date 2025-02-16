import React, { useState, useEffect } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  Paper,
  Alert,
  MenuItem,
  Select,
  FormControl,
  InputLabel
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";

const MemberRegister = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    name: "",
    email: "",
    mobileNumber: "",
    gender: "",
    subscriptionId: ""
  });
  const [subscriptionPlans, setSubscriptionPlans] = useState([]);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchPlans = async () => {
      try {
        const response = await fetch("http://localhost:8080/admin/subscription-plans/all");
        if (!response.ok) {
          throw new Error("Failed to fetch subscription plans");
        }
        const plans = await response.json();
        setSubscriptionPlans(plans);
      } catch (err) {
        console.error("Error fetching subscription plans:", err);
        setError("Failed to load subscription plans.");
      }
    };
    fetchPlans();
  }, []);

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const validateForm = () => {
    if (
      !form.name.trim() ||
      !form.email.trim() ||
      !form.mobileNumber.trim() ||
      !form.gender ||
      !form.subscriptionId
    ) {
      setError("All fields are required!");
      return false;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(form.email)) {
      setError("Please enter a valid email address.");
      return false;
    }
    const mobileRegex = /^[0-9]{10}$/;
    if (!mobileRegex.test(form.mobileNumber)) {
      setError("Mobile number must be exactly 10 digits.");
      return false;
    }
    return true;
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    if (!validateForm()) return;

    setLoading(true);
    try {
      const response = await fetch("http://localhost:8080/members/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (response.ok) {
        const message = await response.text();
        setSuccess(message);
        setTimeout(() => navigate("/member-login"), 3000);
      } else {
        const errorMsg = await response.text();
        setError(errorMsg);
      }
    } catch (err) {
      console.error("Registration error:", err);
      setError("Something went wrong. Please try again later.");
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
          maxWidth: 400,
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
            Member Registration
          </Typography>

          {error && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {error}
            </Alert>
          )}
          {success && (
            <Alert severity="success" sx={{ mb: 2 }}>
              {success}
            </Alert>
          )}

          <TextField
            fullWidth
            label="Name"
            name="name"
            variant="outlined"
            sx={{ mb: 2, backgroundColor: "#fff", borderRadius: "5px" }}
            value={form.name}
            onChange={handleChange}
          />

          <TextField
            fullWidth
            label="Email"
            name="email"
            type="email"
            variant="outlined"
            sx={{ mb: 2, backgroundColor: "#fff", borderRadius: "5px" }}
            value={form.email}
            onChange={handleChange}
          />

          <TextField
            fullWidth
            label="Mobile Number"
            name="mobileNumber"
            variant="outlined"
            sx={{ mb: 2, backgroundColor: "#fff", borderRadius: "5px" }}
            value={form.mobileNumber}
            onChange={handleChange}
          />

          <FormControl fullWidth sx={{ mb: 2, backgroundColor: "#fff", borderRadius: "5px" }}>
            <InputLabel>Gender</InputLabel>
            <Select 
              name="gender" 
              value={form.gender} 
              onChange={handleChange}
              label="Gender"
            >
              <MenuItem value="male">Male</MenuItem>
              <MenuItem value="female">Female</MenuItem>
              <MenuItem value="other">Other</MenuItem>
            </Select>
          </FormControl>

          <FormControl fullWidth sx={{ mb: 2, backgroundColor: "#fff", borderRadius: "5px" }}>
            <InputLabel>Select Subscription Plan</InputLabel>
            <Select 
              name="subscriptionId" 
              value={form.subscriptionId || ""}  
              onChange={handleChange} 
              label="Select Subscription Plan"
            >
              {subscriptionPlans.length > 0 ? (
                subscriptionPlans.map((plan) => (
                  <MenuItem key={plan.id} value={plan.id}>
                    {plan.planName} - ₹{plan.price} ({plan.durationDays} days)
                  </MenuItem>
                ))
              ) : (
                <MenuItem disabled>No plans available</MenuItem>
              )}
            </Select>
          </FormControl>

          <Button
            variant="contained"
            fullWidth
            disabled={loading}
            onClick={handleRegister}
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
            {loading ? "Registering..." : "Register"}
          </Button>
        </Paper>
      </Box>
    </Box>
  );
};

export default MemberRegister;
