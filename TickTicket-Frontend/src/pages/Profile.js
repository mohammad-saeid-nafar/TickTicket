import React from "react";
import { Container, Stack } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
// import Tab from "@mui/material/Tab";
// import Tabs from "@mui/material/Tabs";
// import TabPanel from "../components/TabPanel";
// import ProfileCard from "../components/ProfileCard";

const Profile = () => {
  // const [upcomingEvents, setUpcomingEvents] = useState([]);
  const [users, setUsers] = useState([]);


  const loadData = async () => {
    axios
        .get("users/")
        .then((res) => {
          setUsers(res.data);
          console.log("users", users)

        });
    // console.log(res )
  }

  useEffect(() => {

    loadData();
    // console.log("test");
  }, []);

  return (
      <Container
          sx={{
            paddingTop: "5%",
          }}
      >
          <Stack spacing={2}>
            <h1>Profile</h1>
            {/*<ProfileCard key={user.id} event={user} />*/}
          </Stack>
      </Container>
  );
};
export default Profile;
