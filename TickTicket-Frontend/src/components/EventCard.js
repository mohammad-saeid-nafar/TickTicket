import React from "react";
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
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { Modal, Rating, TextField, Button } from "@mui/material";

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
  const [reviewRating, setReviewRating] = useState(0);
  const [reviewTitle, setReviewTitle] = useState("");
  const [reviewDescription, setReviewDescription] = useState("");

  useEffect(() => {
    loadData();
    // eslint-disable-next-line
  }, []);

  const loadData = async () => {
    setLoading(true);
    await axios.get(`reviews/event/${props.event.id}`).then((res) => {
      setReviews(res.data);
      setLoading(false);
    });
    await axios.get(`reviews/event/${props.event.id}/average`).then((res) => {
      setRating(res.data);
    });
  };

  const handleExpandClick = async () => {
    setExpanded(!expanded);
  };

  const [anchorEl, setAnchorEl] = React.useState(null);
  const actionsOpen = Boolean(anchorEl);
  const handleActionsClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleActionsClose = () => {
    setAnchorEl(null);
  };

  const [reviewOpen, setReviewOpen] = React.useState(false);
  const handleReviewOpen = () => {
    handleActionsClose();
    setReviewOpen(true);
  };
  const handleReviewClose = () => setReviewOpen(false);

  const modalStyle = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: "50%",
    bgcolor: "background.paper",
    boxShadow: 24,
    p: 4,
  };

  const createReview = async () => {
    await axios.post(`reviews`, {
      title: reviewTitle,
      description: reviewDescription,
      rating: reviewRating,
      eventId: props.event.id,
      userId: "00de8b99-e38c-4cb6-b92c-0b3ec484b83e",
    });
    loadData();
    handleReviewClose();
  };

  return (
    <Card>
      <CardHeader
        action={
          <div>
           { props.addReview && <IconButton
              id="basic-button"
              aria-controls={actionsOpen ? "basic-menu" : undefined}
              aria-haspopup="true"
              aria-expanded={actionsOpen ? "true" : undefined}
              onClick={handleActionsClick}
            >
              <MoreVertIcon />
            </IconButton>}
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
            <Modal
              open={reviewOpen}
              onClose={handleReviewClose}
              aria-labelledby="modal-modal-title"
              aria-describedby="modal-modal-description"
            >
              <Card sx={modalStyle}>
                <Box
                  component="form"
                  sx={{
                    "& .MuiTextField-root": { m: 1, width: "25ch" },
                  }}
                  noValidate
                  autoComplete="off"
                >
                  <Typography component="legend">Rating</Typography>
                  <Rating
                    value={reviewRating}
                    onChange={(event, newValue) => {
                      setReviewRating(newValue);
                    }}
                  />
                  <div>
                    <TextField
                      required
                      id="review-title"
                      label="Title"
                      onChange={(event) => setReviewTitle(event.target.value)}
                    />
                    <TextField
                      required
                      multiline
                      id="review-description"
                      label="Description"
                      helperText="Please provide a description of your experience."
                      onChange={(event) =>
                        setReviewDescription(event.target.value)
                      }
                    />
                  </div>
                  <Button variant="outlined" onClick={createReview}>
                    Add Review
                  </Button>
                </Box>
              </Card>
            </Modal>
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
        {reviews.length !== 0 && <ExpandMore
          expand={expanded}
          disabled={loading || reviews.length === 0}
          onClick={handleExpandClick}
          aria-expanded={expanded}
          aria-label="show more"
        >
          <ExpandMoreIcon />
        </ExpandMore>}
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
