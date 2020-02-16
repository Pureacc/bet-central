import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom'
import {CssBaseline, withStyles} from '@material-ui/core'
import SimpleTable from '../content/Bets';
import Calculate from "../content/Calculate";
import Home from "../content/Home";
import Register from "../content/Register";
import SignIn from "../content/SignIn";
import Actions from "../content/Actions";
import TopMenu from "../menu/top/TopMenu";
import LeftMenu from "../menu/left/LeftMenu";
import {SecuredRoute} from "./SecuredRoute";
import {compose} from "recompose";
import {connect} from "react-redux";
import {Landing} from "../content/Landing";
import Message from "../Message";

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
                        <Switch>
                            {this.props.authenticated && <Route exact path="/" component={Home}/>}
                            {this.props.authenticated || <Route exact path="/" component={Landing}/>}
                            <Route path="/register" component={Register}/>
                            <Route path="/signin"
                                   render={(props) => <SignIn {...props} />}/>
                            <SecuredRoute path="/actions" component={Actions}
                                          isAuthenticated={this.props.authenticated}/>
                            <SecuredRoute path="/bets" isAuthenticated={this.props.authenticated} render={() =>
                                <SimpleTable/>
                            }/>
                            <Route path="/calculate" component={Calculate}/>
                        </Switch>
                        <Message/>
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
        padding: theme.spacing(3),
        height: '100vh',
        overflow: 'auto',
    },
});

const mapStateToProps = state => ({
    authenticated: !!state.user.id,
});

export default compose(
    withStyles(styles),
    connect(mapStateToProps),
)(Dashboard);