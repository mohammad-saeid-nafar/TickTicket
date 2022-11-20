import React from "react";
import { Container, Stack } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import ProfileCard from "../components/ProfileCard";

const Home = () => {
  const [pastEvents, setPastEvents] = useState([]);

  useEffect(() => {
    axios
        .get("events/past/" + localStorage.getItem("userId"))
        .then((res) => {
          setPastEvents(res.data);
        });
  }, []);

  return (
      <Container
          sx={{
            paddingTop: "5%",
          }}
      >
          <Stack spacing={2}>
            {pastEvents.map((event) => {
              return <ProfileCard key={event.id} event={event} addReview={true} />;
            })}
          </Stack>
      </Container>
  );
};
export default Home;
