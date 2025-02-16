import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Welcome from "./pages/Welcome";
import AdminLogin from "./pages/AdminLogin";
import AdminDashboard from "./pages/AdminDashboard";
import MemberLogin from "./pages/MemberLogin";
import ChangePassword from "./pages/ChangePassword";
import Navbar from "./components/Navbar";
import MemberRegister from "./pages/MemberRegister";
import AdminMemberManagement from "./pages/AdminMemberManagement";
import Subscription from "./pages/Subscription";
import ManageBooks from "./pages/ManageBooks";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Welcome />} />
        <Route path="/admin-login" element={<AdminLogin />} />
        <Route path="/admin-dashboard" element={<AdminDashboard />} />
        <Route path="/member-login" element={<MemberLogin />} />
        <Route path="/change-password" element={<ChangePassword />} />
        <Route path="/member-register" element={<MemberRegister />} />
        <Route path="/admin/members" element={<AdminMemberManagement />} />
        <Route path="/admin/others/subscription-plan" element={<Subscription/>} />
        <Route path="/admin/books" element={<ManageBooks/>} />
      </Routes>
    </Router>
  );
};

export default App;
