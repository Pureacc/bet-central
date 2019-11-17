import React, {Fragment} from "react";
import {Typography} from "@material-ui/core";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid/Grid";
import InputAdornment from "@material-ui/core/InputAdornment/InputAdornment";

class Calculate extends React.Component {
  constructor(props) {
    super(props);
    this.state = {oddsA: 2, oddsB: 2, vig: 0, noVigOddsA: 2, noVigOddsB: 2};
  }

  handleChangeOdds = event => {
    this.setState({[event.target.name]: event.target.value});
  };

  handleCalculate = event => {
    fetch(`/api/calculate/novig?oddsA=${this.state.oddsA}&oddsB=${this.state.oddsB}`)
      .then(response => response.json())
      .then(data => this.setState({vig: data.vigPercentage, noVigOddsA: data.noVigOddsA, noVigOddsB: data.noVigOddsB}));
  };

  render() {
    return (
      <Fragment>
        <Typography variant="h4" gutterBottom component="h2">
          Calculate
        </Typography>
        <form>
          <Grid item xs container direction="column" spacing={16}>
            <TextField
              id="odds-a"
              name="oddsA"
              label="Odds A"
              value={this.state.oddsA}
              inputProps={{min: "1", step: "0.01"}}
              onChange={this.handleChangeOdds}
              type="number"
              margin="normal"
            />
            <TextField
              id="odds-b"
              name="oddsB"
              label="Odds B"
              value={this.state.oddsB}
              inputProps={{min: "1", step: "0.01"}}
              onChange={this.handleChangeOdds}
              type="number"
              margin="normal"
            />
            <TextField
              id="vig"
              label="Vig"
              value={this.state.vig}
              InputProps={{
                startAdornment: <InputAdornment position="start">%</InputAdornment>,
              }}
              disabled="true"
              type="number"
              margin="normal"
            />
            <TextField
              id="odds-a-no-vig"
              label="Odds A without vig"
              value={this.state.noVigOddsA}
              disabled="true"
              type="number"
              margin="normal"
            />
            <TextField
              id="odds-b-no-vig"
              label="Odds B without vig"
              value={this.state.noVigOddsB}
              disabled="true"
              type="number"
              margin="normal"
            />
            <Button onClick={this.handleCalculate} color="primary" variant="contained">
              Calculate
            </Button>
          </Grid>
        </form>
      </Fragment>
    )
  }
}

export default Calculate