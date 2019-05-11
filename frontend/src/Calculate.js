import React, {Fragment} from "react";
import {Typography} from "@material-ui/core";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";

class Calculate extends React.Component {
  constructor(props) {
    super(props);
    this.state = {odds1: 0};
  }

  handleChange = event => {
    this.setState({odds1: event.target.value});
  };

  render() {
    return (
      <Fragment>
        <Typography variant="h4" gutterBottom component="h2">
          Calculate
        </Typography>
        <form>
          <TextField
            id="odds-a"
            label="Odds A"
            value={this.state.odds1}
            onChange={this.handleChange}
            type="number"
            margin="normal"
          />
          <TextField
            id="standard-number"
            label="Number"
            value={this.state.odds1}
            disabled="true"
            type="number"
            margin="normal"
          />
          <Button color="primary">
            Calculate
          </Button>
        </form>
      </Fragment>
    )
  }
}

export default Calculate