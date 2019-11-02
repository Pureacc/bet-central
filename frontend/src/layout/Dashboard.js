import React from 'react';
import {BrowserRouter, Route} from 'react-router-dom'
import {CssBaseline, withStyles} from '@material-ui/core'
import SimpleTable from '../Bets';
import Calculate from "../Calculate";
import Home from "../Home";
import Register from "../Register";
import SignIn from "../SignIn";
import TopMenu from "../menu/TopMenu";
import LeftMenu from "../menu/LeftMenu";

class Dashboard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            open: true
        }
    }

    handleDrawerOpen = () => {
        this.setState({open: true});
    };

    handleDrawerClose = () => {
        this.setState({open: false});
    };

    render() {
        const {classes} = this.props;

        return (
            <div className={classes.root}>
                <CssBaseline/>

                <BrowserRouter>
                    <TopMenu open={this.state.open}
                             onDrawerOpen={this.handleDrawerOpen}/>
                    <LeftMenu open={this.state.open}
                              onDrawerClose={this.handleDrawerClose}/>

                    <main className={classes.content}>
                        <div className={classes.appBarSpacer}/>
                        <Route exact path="/" render={(props) => <Home {...props} />}/>
                        <Route path="/register" component={Register}/>
                        <Route path="/signin"
                               render={(props) => <SignIn {...props} />}/>
                        <Route path="/calculate" component={Calculate}/>
                        <Route path="/bets" render={() =>
                            <SimpleTable/>
                        }/>
                    </main>

                </BrowserRouter>
            </div>
        );
    }
}

const styles = theme => ({
    root: {
        display: 'flex',
    },
    appBarSpacer: theme.mixins.toolbar,
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 3,
        height: '100vh',
        overflow: 'auto',
    },
});

export default withStyles(styles)(Dashboard);