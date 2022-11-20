import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import { styled } from "@mui/material/styles";
import {
  Box,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  Collapse,
  IconButton,
  List,
  Menu,
  MenuItem,
  Typography,
} from "@mui/material";
import {
  ExpandMore as ExpandMoreIcon,
  MoreVert as MoreVertIcon,
} from "@mui/icons-material";
import Review from "./Review";
import EventRating from "./EventRating";
import ReviewModal from "./ReviewModal";

const EventCard = (props) => {
  const [expanded, setExpanded] = useState(false);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(false);
  const [rating, setRating] = useState(0);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const [reviewOpen, setReviewOpen] = React.useState(false);
  const actionsOpen = Boolean(anchorEl);

  useEffect(() => {
    loadData();
    // eslint-disable-next-line
  }, []);

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

  const loadData = async () => {
    setLoading(true);
    await axios.get(`reviews/event/${props.event.id}`).then((res) => {
      setReviews(res.data);
      setLoading(false);
    });
    await axios.get(`reviews/event/${props.event.id}/average`).then((res) => {
      setRating(Math.round(res.data * 10) / 10);
    });
  };

  const handleExpandClick = async () => {
    setExpanded(!expanded);
  };

  const handleActionsClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleActionsClose = () => {
    setAnchorEl(null);
  };

  const handleReviewOpen = () => {
    handleActionsClose();
    setReviewOpen(true);
  };

  const handleReviewClose = () => setReviewOpen(false);

  const createReview = async (title, description, rating) => {
    await axios.post(`reviews`, {
      title: title,
      description: description,
      rating: rating,
      eventId: props.event.id,
      userId: localStorage.getItem("userId"),
    });
    loadData();
    handleReviewClose();
  };

  return (
    <Card>
      <CardHeader
        action={
          <div>
            {props.addReview &&
              reviews.every(
                (review) => review.user.id !== localStorage.getItem("userId"),
              ) && (
                <IconButton
                  id="basic-button"
                  aria-controls={actionsOpen ? "basic-menu" : undefined}
                  aria-haspopup="true"
                  aria-expanded={actionsOpen ? "true" : undefined}
                  onClick={handleActionsClick}
                >
                  <MoreVertIcon />
                </IconButton>
              )}
            <Menu
              id="basic-menu"
              anchorEl={anchorEl}
              open={actionsOpen}
              onClose={handleActionsClose}
              MenuListProps={{
                "aria-labelledby": "basic-button",
              }}
            >
              <MenuItem onClick={handleReviewOpen}>Add Review</MenuItem>
            </Menu>
            <ReviewModal
              open={reviewOpen}
              handleClose={handleReviewClose}
              handleAction={createReview}
              aria-labelledby="modal-modal-title"
              aria-describedby="modal-modal-description"
            ></ReviewModal>
          </div>
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
        {reviews.length !== 0 && (
          <ExpandMore
            expand={expanded}
            disabled={loading || reviews.length === 0}
            onClick={handleExpandClick}
            aria-expanded={expanded}
            aria-label="show more"
          >
            <ExpandMoreIcon />
          </ExpandMore>
        )}
      </CardActions>
      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <List sx={{ width: "100%", bgcolor: "background.paper" }}>
          {reviews.map((review) => {
            return (
              <Review key={review.id} review={review} loadData={loadData} />
            );
          })}
        </List>
      </Collapse>
    </Card>
  );
};
export default EventCard;
