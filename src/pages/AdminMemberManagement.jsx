import React, { useState, useEffect } from "react";
import {
    Box,
    Tabs,
    Tab,
    Typography,
    Paper,
    Alert,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    Button
} from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { useNavigate } from "react-router-dom";
import Topbar from "../components/Topbar";
import Sidebar from "../components/Sidebar";

function TabPanel(props) {
    const { children, value, index, ...other } = props;
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`admin-tabpanel-${index}`}
            aria-labelledby={`admin-tab-${index}`}
            {...other}
        >
            {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
        </div>
    );
}

const AdminMemberManagement = () => {
    const navigate = useNavigate();
    const [tabIndex, setTabIndex] = useState(0);
    const [allMembers, setAllMembers] = useState([]);
    const [pendingMembers, setPendingMembers] = useState([]);
    const [error, setError] = useState("");
    const [approveDialogOpen, setApproveDialogOpen] = useState(false);
    const [selectedMember, setSelectedMember] = useState(null);
    const [subscriptionPlans, setSubscriptionPlans] = useState([]);
    const [selectedPlan, setSelectedPlan] = useState("");

    const fetchAllMembers = async () => {
        try {
            const response = await fetch("http://localhost:8080/members/all?page=0&size=10");
            if (!response.ok) throw new Error("Failed to fetch members");
            const data = await response.json();
            setAllMembers(data);
        } catch (err) {
            setError(err.message);
        }
    };

    const fetchPendingMembers = async () => {
        try {
            const response = await fetch("http://localhost:8080/members/pending");
            if (!response.ok) throw new Error("Failed to fetch pending members");
            const data = await response.json();
            setPendingMembers(data);
        } catch (err) {
            setError(err.message);
        }
    };

    const fetchSubscriptionPlans = async () => {
        try {
            const response = await fetch("http://localhost:8080/admin/subscription-plans/all");
            if (!response.ok) throw new Error("Failed to fetch subscription plans");
            const data = await response.json();
            setSubscriptionPlans(data);
        } catch (err) {
            console.error("Error fetching subscription plans:", err);
        }
    };

    useEffect(() => {
        fetchAllMembers();
        fetchPendingMembers();
        fetchSubscriptionPlans();
    }, []);

    const handleTabChange = (event, newValue) => {
        setTabIndex(newValue);
    };

    const handleApprove = async () => {
        if (!selectedMember || !selectedPlan) return;
        try {
            const response = await fetch(
                `http://localhost:8080/members/approve/${selectedMember.memberId}/${selectedPlan}`,
                { method: "POST" }
            );
            if (!response.ok) throw new Error("Failed to approve member");
            await fetchPendingMembers();
            await fetchAllMembers();
            setApproveDialogOpen(false);
            setSelectedMember(null);
            setSelectedPlan("");
        } catch (err) {
            setError(err.message);
        }
    };

    const openApproveDialog = (member) => {
        setSelectedMember(member);
        setApproveDialogOpen(true);
    };

    const columns = [
        { field: "memberId", headerName: "ID", width: 80 },
        { field: "name", headerName: "Name", width: 150 },
        { field: "email", headerName: "Email", width: 200 },
        { field: "mobileNumber", headerName: "Mobile", width: 150 },
        { field: "gender", headerName: "Gender", width: 100 },
        { field: "isActive", headerName: "Active", width: 100, type: "boolean" },
    ];

    const pendingColumns = [
        ...columns,
        {
            field: "actions",
            headerName: "Actions",
            width: 150,
            renderCell: (params) => (
                <Button variant="outlined" color="primary" onClick={() => openApproveDialog(params.row)}>
                    Approve
                </Button>
            ),
        },
    ];

    return (
        <Box sx={{ display: "flex", minHeight: "100vh", backgroundColor: "#f5f5f5" }}>
            <Sidebar />
            <Box
                sx={{
                    flexGrow: 1,
                    ml: "240px", 
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
                    Admin Member Management
                </Typography>

                <Tabs value={tabIndex} onChange={handleTabChange} aria-label="Member management tabs">
                    <Tab label="All Members" />
                    <Tab label="Pending Members" />
                </Tabs>

                <TabPanel value={tabIndex} index={0}>
                    <Paper sx={{ height: 400, width: "100%" }}>
                        <DataGrid
                            rows={allMembers}
                            columns={columns}
                            getRowId={(row) => row.memberId}
                            pageSize={10}
                            rowsPerPageOptions={[10]}
                            autoHeight
                        />
                    </Paper>
                </TabPanel>

                <TabPanel value={tabIndex} index={1}>
                    <Paper sx={{ height: 400, width: "100%" }}>
                        <DataGrid
                            rows={pendingMembers}
                            columns={pendingColumns}
                            getRowId={(row) => row.memberId}
                            pageSize={10}
                            rowsPerPageOptions={[10]}
                            autoHeight
                        />
                    </Paper>
                </TabPanel>

                <Dialog open={approveDialogOpen} onClose={() => setApproveDialogOpen(false)}>
                    <DialogTitle>Approve Member</DialogTitle>
                    <DialogContent>
                        <Typography variant="body1" sx={{ mb: 2 }}>
                            Approving member: {selectedMember ? selectedMember.name : ""}
                        </Typography>
                        <FormControl fullWidth>
                            <InputLabel>Select Subscription Plan</InputLabel>
                            <Select value={selectedPlan} onChange={(e) => setSelectedPlan(e.target.value)}>
                                {subscriptionPlans.map((plan) => (
                                    <MenuItem key={plan.id} value={plan.id}>
                                        {plan.planName} - ₹{plan.price} ({plan.durationDays} days)
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={() => setApproveDialogOpen(false)} color="secondary">
                            Cancel
                        </Button>
                        <Button onClick={handleApprove} color="primary">
                            Approve
                        </Button>
                    </DialogActions>
                </Dialog>
            </Box>
        </Box>
    );
};

export default AdminMemberManagement;
