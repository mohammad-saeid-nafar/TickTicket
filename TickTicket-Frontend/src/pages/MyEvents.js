import React from "react";
import { Container, Stack } from "@mui/material";
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

  useEffect(() => {
    axios
      .get("events/past/35f94e94-3f80-4919-b6a8-07a28d855b68")
      .then((res) => {
        setPastEvents(res.data);
      });
    axios
      .get("events/upcoming/35f94e94-3f80-4919-b6a8-07a28d855b68")
      .then((res) => {
        setUpcomingEvents(res.data);
      } );
  }, []);

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
        <Stack spacing={2}>
          {pastEvents.map((event) => {
            return <EventCard key={event.id} event={event} addReview={true} />;
          })}
        </Stack>
      </TabPanel>
      <TabPanel value={tab} index={1}>
        <Stack spacing={2}>
          {upcomingEvents.map((event) => {
            return <EventCard key={event.id} event={event} addReview={false} />;
          })}
        </Stack>
      </TabPanel>
    </Container>
  );
};
export default Home;
