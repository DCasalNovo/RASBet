import React, { useState, useEffect } from 'react';
import { Game } from './Game';
import { Boletim } from './Boletim';
import './GamesTab.css';

const SPORTS = ['any', 'futebol', 'basquetebol', 'tenis', 'motogp'];

export const GamesTab = ({
    sport,
    games,
    userState,
    userId
}) => {

    const [bets, setBets] = useState([]);

    function BoletimLock() {
        if (userState === 'gambler') {
            return (
                <div className='boletimbox'>
                    <div><Boletim bets={bets} userId={userId} /></div>
                </div>
            );
        }
        else return;
    }

    const addBet = (title, winner, cota) => {
        console.log("adicionar")
        const updateBets = [
            // copy the current bets state
            ...bets,
            // now you can add a new object to add to the array
            {
                // add the ids to update database
                //id: users.length + 1,
                // adding a new bet
                title: title,
                winner: winner,
                cota: cota
            }
        ];
        // update the state to the updateBets
        setBets(updateBets);
    }

    const removeBet = (title) => {
        console.log("remover")
        const newbets = []
        bets.forEach((element) => { if (element.title !== title) newbets.push(element) })
        setBets(newbets)
    }

    const changeBet = (title, winner, cota) => {
        const newbets = []
        bets.forEach((element) => { if (element.title !== title) newbets.push(element) })
        newbets.push({
            title: title,
            winner: winner,
            cota: cota
        })
        setBets(newbets);
    }

    const checkSport = SPORTS.includes(sport)
        ? sport
        : SPORTS[0];

    const filtredGames = [];

    games.forEach(function (game) {
        if (checkSport === 'any' || game.sport === checkSport) {
            filtredGames.push(game)
        }
    })

    if (filtredGames.length !== 0) {
        return (
            <>
                <div className='gamestab'>
                    <div className='bets-tab'>
                        {filtredGames.map(game => (
                            <div><Game title={game.title} date={game.date} participants={game.participants}
                                removeBet={removeBet} addBet={addBet} changeBet={changeBet} userState={userState} /></div>
                        ))}
                    </div>
                    <BoletimLock />
                </div>
            </>
        );
    }
    else {
        if (checkSport === 'any') {
            return (
                <h1>Não há jogos disponíveis</h1>
            );
        } else {
            return (
                <h1> Não há jogos de {checkSport} disponíveis </h1>
            );
        }
    }
}

export default GamesTab;
