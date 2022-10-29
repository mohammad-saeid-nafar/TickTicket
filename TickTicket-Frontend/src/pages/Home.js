import { Button, ButtonGroup } from "@mui/material";

const Home = () => {
  return (
    <ButtonGroup variant="contained" aria-label="outlined primary button group">
      <Button href="/signin">Signin</Button>
      <Button href="/signup">Signup</Button>
      <Button href="/profile">Profile</Button>
      <Button href="/events">Events</Button>
      <Button href="/create_event">Create Event</Button>
    </ButtonGroup>
  );
};

export default Home;
