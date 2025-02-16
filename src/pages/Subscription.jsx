import React, { useState, useEffect } from "react";
import {
  Button,
  Grid,
  Typography,
  Card,
  CardContent,
  TextField,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";

const Subscription = () => {
  const [plans, setPlans] = useState([]);
  const [openForm, setOpenForm] = useState(false);
  const [selectedPlanId, setSelectedPlanId] = useState(null);
  const [planName, setPlanName] = useState("");
  const [durationDays, setDurationDays] = useState(0);
  const [price, setPrice] = useState(0);
  const [adminCommission, setAdminCommission] = useState(0);

  useEffect(() => {
    fetch("http://localhost:8080/admin/subscription-plans/all")
      .then((response) => response.json())
      .then((data) => setPlans(data))
      .catch((error) => console.error("Error fetching plans:", error));
  }, []);

  useEffect(() => {
    if (selectedPlanId) {
      fetch(`http://localhost:8080/admin/subscription-plans/${selectedPlanId}`)
        .then((response) => response.json())
        .then((data) => {
          setPlanName(data.planName);
          setDurationDays(data.durationDays);
          setPrice(data.price);
          setAdminCommission(data.adminCommission);
        })
        .catch((error) => console.error("Error fetching plan data:", error));
    }
  }, [selectedPlanId]);

  const handleOpenForm = (planId = null) => {
    setSelectedPlanId(planId);
    setOpenForm(true);
  };

  const handleCloseForm = () => {
    setOpenForm(false);
    setSelectedPlanId(null);
    setPlanName("");
    setDurationDays(0);
    setPrice(0);
    setAdminCommission(0);
  };

  const handleSubmit = () => {
    const url = selectedPlanId
      ? `http://localhost:8080/admin/subscription-plans/update/${selectedPlanId}`
      : "http://localhost:8080/admin/subscription-plans/add";
    const method = selectedPlanId ? "PUT" : "POST";

    fetch(url, {
      method: method,
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        planName,
        durationDays,
        price,
        adminCommission,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          return response.text().then((text) => {
            throw new Error(text || "Error saving plan");
          });
        }
        return response.json();
      })
      .then(() => {
        alert("Subscription Plan saved successfully!");
        fetchPlans(); 
        handleCloseForm(); 
      })
      .catch((error) => {
        console.error("Error saving plan:", error);
        alert("Error saving subscription plan.");
      });
  };

  const handleDelete = (planId) => {
    fetch(`http://localhost:8080/admin/subscription-plans/delete/${planId}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (!response.ok) {
          return response.text().then((text) => {
            throw new Error(text || "Error deleting plan");
          });
        }
        return response.json();
      })
      .then(() => {
        setPlans(plans.filter((plan) => plan.subscriptionId !== planId));
        alert("Subscription Plan deleted successfully!");
      })
      .catch((error) => {
        console.error("Error deleting plan:", error);
        alert("Error deleting subscription plan.");
      });
  };

  const fetchPlans = () => {
    fetch("http://localhost:8080/admin/subscription-plans/all")
      .then((response) => response.json())
      .then((data) => setPlans(data))
      .catch((error) => console.error("Error fetching plans:", error));
  };

  return (
    <div>
      <Typography variant="h5" gutterBottom>
        Subscription Plans
      </Typography>
      <Button variant="contained" color="primary" onClick={() => handleOpenForm()}>
        Add Subscription Plan
      </Button>

      <Grid container spacing={2}>
        {plans.map((plan) => (
          <Grid item xs={12} sm={6} md={4} key={plan.subscriptionId}>
            <Card>
              <CardContent>
                <Typography variant="h6">{plan.planName}</Typography>
                <Typography>Duration: {plan.durationDays} days</Typography>
                <Typography>Price: ₹{plan.price}</Typography>
                <Typography>Admin Commission: ₹{plan.adminCommission}</Typography>
                <Button
                  variant="contained"
                  color="secondary"
                  onClick={() => handleDelete(plan.subscriptionId)}
                >
                  Delete
                </Button>
                <Button
                  variant="contained"
                  color="primary"
                  onClick={() => handleOpenForm(plan.subscriptionId)}
                >
                  Update
                </Button>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      <Dialog open={openForm} onClose={handleCloseForm}>
        <DialogTitle>{selectedPlanId ? "Update" : "Add"} Subscription Plan</DialogTitle>
        <DialogContent>
          <TextField
            label="Plan Name"
            fullWidth
            value={planName || ""}
            onChange={(e) => setPlanName(e.target.value)}
            margin="normal"
          />
          <TextField
            label="Duration (Days)"
            type="number"
            fullWidth
            value={durationDays || 0}
            onChange={(e) => setDurationDays(Number(e.target.value))}
            margin="normal"
          />
          <TextField
            label="Price"
            type="number"
            fullWidth
            value={price || 0}
            onChange={(e) => setPrice(Number(e.target.value))}
            margin="normal"
          />
          <TextField
            label="Admin Commission"
            type="number"
            fullWidth
            value={adminCommission || 0}
            onChange={(e) => setAdminCommission(Number(e.target.value))}
            margin="normal"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseForm} color="primary">
            Cancel
          </Button>
          <Button onClick={handleSubmit} color="primary">
            {selectedPlanId ? "Update" : "Add"} Plan
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default Subscription;
