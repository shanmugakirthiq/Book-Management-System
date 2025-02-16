import React, { useState } from "react";
import {
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Collapse,
} from "@mui/material";
import {
  Dashboard,
  Book,
  People,
  Assignment,
  Settings,
  ExpandLess,
  ExpandMore,
} from "@mui/icons-material";
import { useNavigate, useLocation } from "react-router-dom";

const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [openMembers, setOpenMembers] = useState(false);
  const [openOthers, setOpenOthers] = useState(false);

  const menuItems = [
    { text: "Dashboard", icon: <Dashboard />, path: "/admin/dashboard" },
    { text: "Books", icon: <Book />, path: "/admin/books" },
    {
      text: "Members",
      icon: <People />,
      submenu: [
        { text: "Manage Member", path: "/admin/members" },
        { text: "Modify Members", path: "/admin/members/modify" },
      ],
    },
    { text: "Rentals", icon: <Assignment />, path: "/admin/rentals" },
    {
      text: "Others",
      icon: <Settings />,
      submenu: [
        {
          text: "Add Subscription Plan",
          path: "/admin/others/subscription-plan",
        },
      ],
    },
  ];

  const handleDropdownClick = (menuText) => {
    if (menuText === "Members") {
      setOpenMembers(!openMembers);
    } else if (menuText === "Others") {
      setOpenOthers(!openOthers);
    }
  };

  return (
    <Drawer
      variant="permanent"
      sx={{
        width: 240,
        flexShrink: 0,
        "& .MuiDrawer-paper": {
          width: 240,
          boxSizing: "border-box",
          backgroundColor: "#1E88E5",
          color: "#fff",
          marginTop: "64px",
          height: "calc(100vh - 64px)",
        },
      }}
    >
      <List>
        {menuItems.map((item) => {
          if (item.submenu) {
            const isSubItemActive = item.submenu.some(
              (subItem) => location.pathname === subItem.path
            );

            const isOpen = item.text === "Members" ? openMembers : openOthers;

            return (
              <React.Fragment key={item.text}>
                <ListItem disablePadding>
                  <ListItemButton
                    onClick={() => handleDropdownClick(item.text)}
                    sx={{
                      backgroundColor: isSubItemActive
                        ? "rgba(255,255,255,0.2)"
                        : "transparent",
                      "&:hover": { backgroundColor: "rgba(255,255,255,0.3)" },
                    }}
                  >
                    <ListItemIcon sx={{ color: "#fff" }}>
                      {item.icon}
                    </ListItemIcon>
                    <ListItemText primary={item.text} />
                    {isOpen ? (
                      <ExpandLess sx={{ color: "#fff" }} />
                    ) : (
                      <ExpandMore sx={{ color: "#fff" }} />
                    )}
                  </ListItemButton>
                </ListItem>
                <Collapse in={isOpen} timeout="auto" unmountOnExit>
                  <List component="div" disablePadding>
                    {item.submenu.map((subItem) => (
                      <ListItem key={subItem.text} disablePadding>
                        <ListItemButton
                          onClick={() => navigate(subItem.path)}
                          sx={{
                            pl: 4,
                            backgroundColor:
                              location.pathname === subItem.path
                                ? "rgba(255,255,255,0.2)"
                                : "transparent",
                            "&:hover": {
                              backgroundColor: "rgba(255,255,255,0.3)",
                            },
                          }}
                        >
                          <ListItemText primary={subItem.text} />
                        </ListItemButton>
                      </ListItem>
                    ))}
                  </List>
                </Collapse>
              </React.Fragment>
            );
          } else {
            return (
              <ListItem key={item.text} disablePadding>
                <ListItemButton
                  onClick={() => navigate(item.path)}
                  sx={{
                    backgroundColor:
                      location.pathname === item.path
                        ? "rgba(255,255,255,0.2)"
                        : "transparent",
                    "&:hover": { backgroundColor: "rgba(255,255,255,0.3)" },
                  }}
                >
                  <ListItemIcon sx={{ color: "#fff" }}>
                    {item.icon}
                  </ListItemIcon>
                  <ListItemText primary={item.text} />
                </ListItemButton>
              </ListItem>
            );
          }
        })}
      </List>
    </Drawer>
  );
};

export default Sidebar;
