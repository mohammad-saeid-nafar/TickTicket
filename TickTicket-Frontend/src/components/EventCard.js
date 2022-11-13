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
import Rating from "@mui/material/Rating";
import Box from "@mui/material/Box";

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
        title={props.event.name}
        subheader={props.event.address}
      />
      <CardContent>
        <Typography variant="body2" color="text.secondary">
          {props.event.description}
        </Typography>
      </CardContent>
      <CardActions disableSpacing>
        <Box
          sx={{
            display: "flex",
            width: "25%"
          }}
        >
          {reviews.length === 0 ? (
            <Typography
              variant="body2"
              color="text.secondary"
              sx={{marginLeft: "3%"}}
            >
              No reviews yet
            </Typography>
          ) : (
            <>
              <Typography
                sx={{marginLeft: "3%", marginRight: "5px" }}
                color="text.secondary"
              >
                {rating}
              </Typography>
              <Rating name="read-only" value={rating} readOnly />
              <Typography sx={{marginLeft: "5px" }} color="text.secondary">
                ({reviews.length})
              </Typography>
            </>
          )}
        </Box>
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
        {reviews.map((review) => {
          return (
            <CardContent key={review.id}>
              <Typography paragraph>{review.title}</Typography>
              <Typography paragraph>{review.description}</Typography>
              <Typography paragraph>{review.rating}</Typography>
            </CardContent>
          );
        })}
      </Collapse>
    </Card>
  );
};
export default EventCard;
