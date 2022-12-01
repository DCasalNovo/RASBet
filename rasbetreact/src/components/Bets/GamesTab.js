import React, { useState, useEffect } from 'react';
import { Game } from './Game';
import { Boletim } from './Boletim';
import './GamesTab.css';
import Sport from '../pages/Sport';

export const GamesTab = ({
    games,
    sportType,
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

    const addBet = (id, title, winner, cota, participantId) => {
        console.log("adicionar")
        const updateBets = [
            ...bets,
            {
                id: id,
                title: title,
                winner: winner,
                cota: cota,
                participantId: participantId
            }
        ]
        setBets(updateBets)
    }

    const removeBet = (id) => {
        console.log("remover")
        const newbets = []
        bets.forEach((element) => { if (element.id !== id) newbets.push(element) })
        setBets(newbets)
    }

    const changeBet = (id, title, winner, cota, participantId) => {
        const newbets = []
        bets.forEach((element) => { if (element.id !== id) newbets.push(element) })
        newbets.push({
            id: id,
            title: title,
            winner: winner,
            cota: cota,
            participantId: participantId
        })
        setBets(newbets);
    }

    if (games.length !== 0) {
        return (
            <>
                <div className='gamestab'>
                    <div className='bets-tab'>
                        {games.map(game => (
                            <div key={game.id}><Game id={game.id} title={game.title} date={game.date} participants={game.participants}
                                removeBet={removeBet} addBet={addBet} changeBet={changeBet} userState={userState} /></div>
                        ))}
                    </div>
                    <BoletimLock />
                </div>
            </>
        );
    }
    else {
        if (sportType === 'any') {
            return (
                <h1>Não há jogos disponíveis</h1>
            );
        } else {
            return (
                <h1> Não há jogos de {sportType} disponíveis </h1>
            );
        }
    }
}

export default GamesTab;
