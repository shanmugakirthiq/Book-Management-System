import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Container,
  TextField,
  Button,
  Typography,
  Box,
  Alert,
  Card,
  CardContent,
} from "@mui/material";

const ChangePassword = () => {
  const [newPassword, setNewPassword] = useState("");
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const navigate = useNavigate();
  const memberId = sessionStorage.getItem("memberId");

  const handleChangePassword = async () => {
    try {
      const response = await fetch(`http://localhost:8080/members/change-password/${memberId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ newPassword }),
      });

      if (!response.ok) {
        throw new Error("Failed to change password");
      }

      setSuccess("Password changed successfully! Redirecting to login...");
      setTimeout(() => {
        sessionStorage.removeItem("memberId");
        navigate("/member-login");
      }, 2000);
    } catch (error) {
      setError(error.message);
    }
  };

  return (
    <Container maxWidth="xs">
      <Box mt={8} display="flex" justifyContent="center">
        <Card sx={{ width: "100%", boxShadow: 3, borderRadius: 2 }}>
          <CardContent>
            <Typography variant="h5" textAlign="center" fontWeight="bold" color="secondary" gutterBottom>
              Change Password
            </Typography>

            {error && <Alert severity="error">{error}</Alert>}
            {success && <Alert severity="success">{success}</Alert>}

            <TextField
              label="New Password"
              type="password"
              fullWidth
              margin="normal"
              variant="outlined"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
            />
            <Button
              variant="contained"
              color="secondary"
              fullWidth
              sx={{ mt: 2 }}
              onClick={handleChangePassword}
            >
              Change Password
            </Button>
          </CardContent>
        </Card>
      </Box>
    </Container>
  );
};

export default ChangePassword;
