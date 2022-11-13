import { useState, useEffect } from "react";
import axios from "axios";
import { styled } from "@mui/material/styles";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Collapse from "@mui/material/Collapse";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import Review from "./Review";
import EventRating from "./EventRating";

const EventCard = (props) => {
  const ExpandMore = styled((props) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
  })(({ theme, expand }) => ({
    transform: !expand ? "rotate(0deg)" : "rotate(180deg)",
    marginLeft: "auto",
    transition: theme.transitions.create("transform", {
      duration: theme.transitions.duration.shortest,
    }),
  }));

  const [expanded, setExpanded] = useState(false);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(false);
  const [rating, setRating] = useState(0);

  useEffect(() => {
    setLoading(true);
    axios.get(`/reviews/event/${props.event.id}`).then((res) => {
      setReviews(res.data);
      setLoading(false);
    });
    axios.get(`reviews/event/${props.event.id}/average`).then((res) => {
      setRating(res.data);
    });
    // eslint-disable-next-line
  }, []);

  const handleExpandClick = async () => {
    setExpanded(!expanded);
  };

  return (
    <Card>
      <CardHeader
        action={
          <IconButton aria-label="settings">
            <MoreVertIcon />
          </IconButton>
        }
        title={
          <Box
            sx={{
              display: "flex",
            }}
          >
            <Typography>{props.event.name}</Typography>
            <EventRating reviews={reviews} rating={rating} />
          </Box>
        }
        subheader={props.event.address}
      />
      <CardContent>
        <Typography variant="body2" color="text.secondary">
          {props.event.description}
        </Typography>
      </CardContent>
      <CardActions disableSpacing>
        <ExpandMore
          expand={expanded}
          disabled={loading || reviews.length === 0}
          onClick={handleExpandClick}
          aria-expanded={expanded}
          aria-label="show more"
        >
          <ExpandMoreIcon />
        </ExpandMore>
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <List sx={{ width: "100%", bgcolor: "background.paper" }}>
          {reviews.map((review) => {
            return <Review key={review.id} review={review} />;
          })}
        </List>
      </Collapse>
    </Card>
  );
};
export default EventCard;
