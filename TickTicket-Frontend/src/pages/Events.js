import * as React from 'react';
// import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
// import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
// import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import IconButton from "@mui/material/IconButton";
import MoreVertIcon from '@mui/icons-material/MoreVert';
// import {useEffect} from "react";
// import axios from "axios";


const Events = () => {
    // const [events, setEvents] = React.useState([]);
    //
    // getEvents(() => {
    //     axios.get("/organizer/organizer1").then((res) => {
    //         setEvents(res.data);
    //     });
    // }, []);

    // const bull = (
    //     <Box
    //         component="span"
    //         sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}
    //     >
    //         •
    //     </Box>
    // );

  return (
      <Card sx={{ maxWidth: 300, maxHeight : 400 }}>
        <CardContent>
           <div>
            <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                2022-01-01
            </Typography>
            <IconButton aria-label="settings" textAlign="left">
                <MoreVertIcon />
            </IconButton>
          </div>

          <Typography variant="h5" component="div">
            Justin Bieber World Tour
          </Typography>
          <Typography sx={{ mb: 1.5 }} color="text.secondary">
            250$
          </Typography>
          <Typography variant="body2">
            For all the Beliebers out there, Justin Bieber is coming to town!
          </Typography>
          <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
              <br/>
              Location: Bell Center, Montreal
              <br/>
            Contact: justinbieber@hotmail.com, 514-555-5555
          </Typography>
        </CardContent>
        {/*<CardActions>*/}
        {/*  <Button size="small">Learn More</Button>*/}
        {/*</CardActions>*/}
      </Card>
  );
};
export default Events;
