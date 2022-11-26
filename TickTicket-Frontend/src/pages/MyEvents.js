import React from "react";
import { Box, Container, Stack, Typography } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import EventCard from "../components/EventCard";
import Tab from "@mui/material/Tab";
import Tabs from "@mui/material/Tabs";
import TabPanel from "../components/TabPanel";

const Home = () => {
  const [upcomingEvents, setUpcomingEvents] = useState([]);
  const [pastEvents, setPastEvents] = useState([]);
  const [tab, setTab] = React.useState(0);

  const handleChange = (event, newValue) => {
    setTab(newValue);
  };

  useEffect(() => loadData, []);

  const loadData = async () => {
    await axios
      .get(`events/past/${localStorage.getItem("userId")}`)
      .then((res) => {
        setPastEvents(res.data);
      })
      .catch((err) => {
        console.log(err);
        setPastEvents([]);
      });
    await axios
      .get(`events/upcoming/${localStorage.getItem("userId")}`)
      .then((res) => {
        setUpcomingEvents(res.data);
      })
      .catch((err) => {
        console.log(err);
        setUpcomingEvents([]);
      });
  };

  return (
    <Container
      sx={{
        paddingTop: "5%",
      }}
    >
      <Tabs value={tab} onChange={handleChange} centered>
        <Tab label="Past" />
        <Tab label="Upcoming" />
      </Tabs>
      <TabPanel value={tab} index={0}>
        {pastEvents.length !== 0 ? (
          <Stack spacing={2}>
            {pastEvents.map((event) => {
              return (
                <EventCard key={event.id} event={event} addReview={true} />
              );
            })}
          </Stack>
        ) : (
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Typography variant="body2" color="text.secondary">
              {" "}
              No tickets to display
            </Typography>
          </Box>
        )}
      </TabPanel>
      <TabPanel value={tab} index={1}>
        {upcomingEvents.length !== 0 ? (
          <Stack spacing={2}>
            {upcomingEvents.map((event) => {
              return (
                <EventCard key={event.id} event={event} addReview={false} refresh={loadData}/>
              );
            })}
          </Stack>
        ) : (
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Typography variant="body2" color="text.secondary">
              {" "}
              No tickets to display
            </Typography>
          </Box>
        )}
      </TabPanel>
    </Container>
  );
};
export default Home;
