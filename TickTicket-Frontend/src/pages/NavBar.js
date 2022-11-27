import * as React from "react";
import { Link } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import Menu from "@mui/material/Menu";
import MenuIcon from "@mui/icons-material/Menu";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import MenuItem from "@mui/material/MenuItem";
import LocalActivityIcon from "@mui/icons-material/LocalActivity";
import AccountCircle from "@mui/icons-material/AccountCircle";

function NavBar() {
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [isSignedIn, setIsSignedIn] = React.useState(
    localStorage.getItem("userId") !== null,
  );
  const [anchorElAccountMenu, setAnchorElAccountMenu] = React.useState(null);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const logout = () => {
    localStorage.removeItem("userId");
    setIsSignedIn(false);
    window.location.href = "/";
  };

  const handleAccountMenu = (event) => {
    setAnchorElAccountMenu(event.currentTarget);
  };

  const handleAccountMenuClose = () => {
    setAnchorElAccountMenu(null);
  };

  const pages = isSignedIn
    ? [
        { name: "Events", link: "/events" },
        { name: "Event Types", link: "/event_types" },
        { name: "Organize Event", link: "/create_event" },
        { name: "My Tickets", link: "/my_tickets" },
      ]
    : [
        { name: "Events", link: "/events" },
        { name: "Event Types", link: "/event_types" },
        { name: "Organize Event", link: "/signin" },
      ];

  const accountMenuPages = isSignedIn
    ? [{ name: "Profile", link: "/profile" }]
    : [
        { name: "Sign up", link: "/signup" },
        { name: "Sign in", link: "/signin" },
      ];

  return (
    <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <LocalActivityIcon
            sx={{ display: { xs: "none", md: "flex" }, mr: 1 }}
          />
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: "none", md: "flex" },
              fontFamily: "monospace",
              fontWeight: 700,
              letterSpacing: ".3rem",
              color: "inherit",
              textDecoration: "none",
            }}
          >
            TickTicket
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: "flex", md: "none" } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: "bottom",
                horizontal: "left",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "left",
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: "block", md: "none" },
              }}
            >
              {pages.map((page) => (
                <MenuItem
                  component={Link}
                  to={page.link}
                  key={page.name}
                  onClick={handleCloseNavMenu}
                >
                  <Typography textAlign="center">{page.name}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          <LocalActivityIcon
            sx={{ display: { xs: "flex", md: "none" }, mr: 1 }}
          />
          <Typography
            variant="h5"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: "flex", md: "none" },
              flexGrow: 1,
              fontFamily: "monospace",
              fontWeight: 700,
              letterSpacing: ".3rem",
              color: "inherit",
              textDecoration: "none",
            }}
          >
            TickTicket
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
            {pages.map((page) => (
              <Button
                href={page.link}
                key={page.name}
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: "white", display: "block" }}
              >
                {page.name}
              </Button>
            ))}
          </Box>
          <div>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleAccountMenu}
              color="inherit"
            >
              <AccountCircle />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElAccountMenu}
              anchorOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              open={Boolean(anchorElAccountMenu)}
              onClose={handleAccountMenuClose}
            >
              {accountMenuPages.map((page) => (
                <MenuItem
                  component={Link}
                  to={page.link}
                  key={page.name}
                  onClick={handleAccountMenuClose}
                >
                  <Typography textAlign="center">{page.name}</Typography>
                </MenuItem>
              ))}
              {isSignedIn && (
                <MenuItem onClick={logout}>
                  <Typography textAlign="center">Logout</Typography>
                </MenuItem>
              )}
            </Menu>
          </div>
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default NavBar;
