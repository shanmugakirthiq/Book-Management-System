import React, { useEffect, useState } from "react";
import { Box, Typography, Grid, Paper, IconButton } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { BarChart } from "@mui/x-charts";
import { Dashboard, People, LibraryBooks, MonetizationOn, Warning } from "@mui/icons-material";
import Sidebar from "../components/Sidebar";
import Topbar from "../components/Topbar";

const AdminDashboard = () => {
  const [dashboardData, setDashboardData] = useState({
    totalBooks: 0,
    booksOnRent: 0,
    lostBooks: 0,
    penaltyCollected: 0,
    membershipFees: 0,
    commissionEarned: 0,
  });

  const [lowStockBooks, setLowStockBooks] = useState([]);

  useEffect(() => {
    fetchDashboardData();
    fetchLowStockBooks();
  }, []);

  const fetchDashboardData = async () => {
    try {
      const response = await fetch("http://localhost:8080/admin/dashboard");
      const data = await response.json();
      setDashboardData(data);
    } catch (error) {
      console.error("Error fetching dashboard data:", error);
    }
  };

  const fetchLowStockBooks = async () => {
    try {
      const response = await fetch("http://localhost:8080/books/low-stock");
      const data = await response.json();
      setLowStockBooks(data);
    } catch (error) {
      console.error("Error fetching low stock books:", error);
    }
  };

  return (
    <Box sx={{ display: "flex", minHeight: "100vh", backgroundColor: "#f5f5f5" }}>
      <Sidebar />

      <Box
        sx={{
          flexGrow: 1,
          mr: "120px",
          mt: "64px",
          p: 3,
          overflowY: "auto",
          scrollbarWidth: "none", 
          msOverflowStyle: "none", 
          "&::-webkit-scrollbar": { display: "none" }, 
        }}
      >
        <Topbar />

        <Typography variant="h4" fontWeight="bold" sx={{ mb: 3 }}>
          Admin Dashboard
        </Typography>

        <Grid container spacing={3}>
          {[
            { label: "Total Books", value: dashboardData.totalBooks, icon: <LibraryBooks />, color: "#1E88E5" },
            { label: "Books on Rent", value: dashboardData.booksOnRent, icon: <Dashboard />, color: "#43A047" },
            { label: "Lost Books", value: dashboardData.lostBooks, icon: <Warning />, color: "#E53935" },
            { label: "Penalty Collected", value: `₹${dashboardData.penaltyCollected}`, icon: <MonetizationOn />, color: "#FB8C00" },
            { label: "Membership Fees", value: `₹${dashboardData.membershipFees}`, icon: <People />, color: "#8E24AA" },
            { label: "Commission Earned", value: `₹${dashboardData.commissionEarned}`, icon: <MonetizationOn />, color: "#00897B" },
          ].map((card, index) => (
            <Grid item xs={12} sm={6} md={4} key={index}>
              <Paper elevation={4} sx={{ p: 3, display: "flex", alignItems: "center", borderRadius: 3 }}>
                <IconButton sx={{ bgcolor: card.color, color: "#fff", p: 2, mr: 2 }}>
                  {card.icon}
                </IconButton>
                <Box>
                  <Typography variant="subtitle1">{card.label}</Typography>
                  <Typography variant="h5" fontWeight="bold">
                    {card.value}
                  </Typography>
                </Box>
              </Paper>
            </Grid>
          ))}
        </Grid>

        <Grid container spacing={3} sx={{ mt: 4 }}>
          <Grid item xs={12} md={6}>
            <Paper elevation={4} sx={{ p: 3, borderRadius: 3 }}>
              <Typography variant="h6" sx={{ mb: 2 }}>
                Membership & Rental Statistics
              </Typography>
              <BarChart
                width={400}
                height={350}
                series={[{ data: [dashboardData.totalBooks, dashboardData.booksOnRent, dashboardData.lostBooks], label: "Books Data" }]}
                xAxis={[{ scaleType: "band", data: ["Total Books", "On Rent", "Lost"] }]}
              />
            </Paper>
          </Grid>

          <Grid item xs={12} md={6}>
            <Paper elevation={4} sx={{ p: 3, borderRadius: 3 }}>
              <Typography variant="h6" sx={{ mb: 2 }}>
                Low Stock Books
              </Typography>
              <DataGrid
                rows={lowStockBooks}
                columns={[
                  { field: "id", headerName: "Book ID", width: 80 },
                  { field: "name", headerName: "Book Name", width: 180 },
                  { field: "genre", headerName: "Genre", width: 120 },
                  { field: "author", headerName: "Author", width: 180 },
                  { field: "stocksAvailable", headerName: "Stock", width: 100 },
                ]}
                autoHeight
              />
            </Paper>
          </Grid>
        </Grid>
      </Box>
    </Box>
  );
};

export default AdminDashboard;
