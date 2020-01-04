import * as React from "react";
import {Fragment} from "react";
import {Typography} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import {deposit} from "../../actions/user";
import {connect} from "react-redux";

class Actions extends React.Component {
    constructor(props) {
        super(props);
        this.state = {depositAmount: 50}
    }

    handleDeposit = event => {
        const {actions, userId} = this.props;
        const {depositAmount} = this.state;
        actions.deposit(userId, depositAmount)
    };

    render() {
        return (
            <Fragment>
                <Typography variant="h4" gutterBottom component="h2">
                    Actions
                </Typography>
                <form>
                    {/*<Grid container direction="row" spacing={4}>*/}
                        <Grid item container xs direction="column">
                            <TextField
                                id="deposit-input"
                                label="Deposit amount"
                                value={this.state.depositAmount}
                                inputProps={{min: "1", step: "1"}}
                                type="number"
                                margin="normal"
                            />
                            <Button onClick={this.handleDeposit} color="primary" variant="contained">
                                Deposit
                            </Button>
                        </Grid>
                    {/*</Grid>*/}
                </form>
            </Fragment>
        )
    }
}

const mapStateToProps = state => {
    return {
        userId: state.user.id
    }
};

const mapDispatchToProps = dispatch => {
    return {
        actions: {
            deposit: (userId, euros) => dispatch(deposit(userId, euros))
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Actions);